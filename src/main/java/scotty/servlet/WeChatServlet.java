package scotty.servlet;

import scotty.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WeChatServlet extends HttpServlet {

    public static final Integer ALEXA_TIMEOUT = 6000;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String echo = request.getParameter("echostr");

        //verify

        if (echo != null && !echo.equals("")) {
            HttpUtils.writeText(response, echo);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String amazonId = request.getParameter("userId");
//        String query = request.getParameter("query");
//
//        String userId = UserInformationManager.getUserIdByAmazonId(amazonId);
//
//        String reply = UserQueryManager.getUnreviewedReply(userId, query, ALEXA_TIMEOUT);
//
//        HttpUtils.writeText(response, reply);
    }
}
