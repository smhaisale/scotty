package scotty.servlet;

import scotty.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static scotty.util.FacebookUtils.RECEIVE_CLIENT;

public class MessengerServlet extends HttpServlet {



    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpUtils.writeText(response, request.getParameter("hub.challenge"));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String payload = HttpUtils.getRequestBody(request);
        String signature = request.getHeader("X-Hub-Signature");

        try {
            RECEIVE_CLIENT.processCallbackPayload(payload, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
