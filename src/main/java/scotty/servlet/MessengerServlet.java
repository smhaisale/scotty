package scotty.servlet;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;

import scotty.manager.DialogHistoryManager;
import scotty.manager.UserInformationManager;
import scotty.manager.UserQueryManager;
import scotty.util.HttpUtils;
import scotty.util.SystemUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static scotty.common.Config.*;

public class MessengerServlet extends HttpServlet {

    public static MessengerSendClient SEND_CLIENT = MessengerPlatform.newSendClientBuilder(SCOTTY_PAGE_ACCESS_TOKEN).build();

    public static MessengerReceiveClient RECEIVE_CLIENT = MessengerPlatform.newReceiveClientBuilder(SCOTTY_APP_SECRET, SCOTTY_MESSENGER_TOKEN)
            .onTextMessageEvent(event -> {
                try {
                    String facebookId = event.getSender().getId();
                    String text = event.getText();

                    String userId = UserInformationManager.getUserIdByFacebookId(facebookId);

                    DialogHistoryManager.addEntry(userId, "messenger", text);

                    String reply = UserQueryManager.getReviewedReply(userId, text);

                    if (reply != null && !reply.equals("")) {
                        DialogHistoryManager.addEntry(userId, "system", reply);
                        SEND_CLIENT.sendTextMessage(facebookId, reply);
                    }

                } catch (Exception e) {
                    SystemUtils.log("MessengerServlet", "Exception occurred while processing message.");
                    e.printStackTrace();
                }
            }).build();


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
