package scotty.servlet;

import scotty.common.HttpUtils;
import scotty.database.WozReview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static scotty.common.Config.CHATBOT_ADDRESS;
import static scotty.common.Config.WOZ_WAIT_TIMEOUT;

public class UserRequestServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST");

        try {
            String query = request.getParameter("query");
            String answer = HttpUtils.remoteGet(CHATBOT_ADDRESS + "?query=" + query);
            WozReview.put(query, answer);

            Thread.sleep(WOZ_WAIT_TIMEOUT);

            HttpUtils.writeText(response, WozReview.delete(query));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
