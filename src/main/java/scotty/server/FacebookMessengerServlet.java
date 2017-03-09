package scotty.server;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;

import scotty.manager.WozReviewManager;
import scotty.util.HttpUtils;
import scotty.dao.ChatbotServiceDao;
import scotty.dao.DialogHistoryDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static scotty.common.Config.*;

public class FacebookMessengerServlet extends HttpServlet {

    private WozReviewManager manager = new WozReviewManager();

    private MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(SCOTTY_PAGE_ACCESS_TOKEN).build();

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

            MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder(SCOTTY_APP_SECRET, SCOTTY_MESSENGER_TOKEN)
                    .onTextMessageEvent(event ->  {

                        try {
                            String userId = event.getSender().getId();
                            String text = event.getText();

                            System.out.println("Received\t" + userId + "\t" + text);

                            String reply = ChatbotServiceDao.getReply(userId, text, DialogHistoryDao.get(userId));

                            DialogHistoryDao.add(userId, text);
                            DialogHistoryDao.add(userId, reply);

                            reply = manager.review(userId, text, reply);

                            System.out.println("Replying with\t" + reply);

                            sendClient.sendTextMessage(userId, reply);

                        } catch (Exception e) {

                        }
                    })
                    .build();

            receiveClient.processCallbackPayload(payload, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
