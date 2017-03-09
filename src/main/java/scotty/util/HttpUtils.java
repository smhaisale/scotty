package scotty.util;

import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static final boolean log = false;

    public static String getRequestBody(HttpServletRequest request) throws IOException {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Get request body for " + request);
        }

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

    // From: http://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-java
    public static String doGet(String urlAddress) throws IOException {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Do review request for " + urlAddress);
        }

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
    public static String doPost(String urlAddress) throws IOException {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Do post request for " + urlAddress);
        }

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

    public static String doPostWithParams(String requestURL, HashMap<String, String> postDataParams) {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Do post request for " + requestURL + " with params " + postDataParams);
        }

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static void write(HttpServletResponse response, String text) throws IOException {

        PrintWriter out = response.getWriter();
        if (text != null) {
            out.print(text);
        }
        out.flush();
        out.close();
    }

    public static void writeJSON(HttpServletResponse response, JSONObject json) throws IOException {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Write JSON " + json + " to " + response);
        }

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");

        json.writeJSONString(response.getWriter());

        write(response, null);
    }

    public static void writeText(HttpServletResponse response, String text) throws IOException {

        if (log) {
            SystemUtils.log(new HttpUtils(), "Write text " + text + " to " + response);
        }

        response.setStatus(200);
        response.setContentType("text/plain");

        write(response, text);

    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
