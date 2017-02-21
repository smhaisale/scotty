package scotty.common;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

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
