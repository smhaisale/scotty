package scotty.common;

import org.json.simple.JSONObject;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

public class HttpUtils {

    public static String getRequestBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    public static SSLContext getSSLContext() throws Exception {
        SSLContext context;
        KeyManagerFactory kmf;
        KeyStore ks;
        char[] storepass = "wozuser".toCharArray();
        char[] keypass = "wozuser".toCharArray();
        String storename = "/home/ec2-user/mykeystore.jks";

        context = SSLContext.getInstance("TLS");
        kmf = KeyManagerFactory.getInstance("SunX509");
        FileInputStream fin = new FileInputStream(storename);
        ks = KeyStore.getInstance("JKS");
        ks.load(fin, storepass);

        kmf.init(ks, keypass);
        context.init(kmf.getKeyManagers(), null, null);

        return context;
    }

    // From: http://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-java
    public static String remoteGet(String urlAddress) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    // From: http://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-java
    public static String remotePost(String urlAddress) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static void write(HttpServletResponse response, String text) throws IOException {

        PrintWriter out = response.getWriter();
        if (text != null) {
            out.print(text);
        }
        out.flush();
        out.close();
    }

    public static void writeJSON(HttpServletResponse response, JSONObject json) throws IOException {

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");

        json.writeJSONString(response.getWriter());

        write(response, null);
    }

    public static void writeText(HttpServletResponse response, String answer) throws IOException {

        response.setStatus(200);
        response.setContentType("text/plain");

        write(response, answer);

    }
}
