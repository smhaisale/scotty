package scotty.servlet;

import scotty.common.HttpUtils;
import scotty.database.WozReview;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static scotty.common.Config.CHATBOT_ADDRESS;

public class WozRequestServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST");

        Map<String, String> map = WozReview.get();

        //IMPORTANT: only works for Map<String, String>
        JSONObject json = new JSONObject(map);

        HttpUtils.writeJSON(response, json);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String query = request.getParameter("query");
        String answer = request.getParameter("answer");

        WozReview.put(query, answer);

        //TODO: Somehow wake up waiting user thread.

        String url = CHATBOT_ADDRESS + "?query=" + query + "&answer=" + answer;
        HttpUtils.remotePost(url);
    }
}
