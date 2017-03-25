package scotty.util;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import scotty.manager.DialogHistoryManager;
import scotty.manager.UserInformationManager;
import scotty.manager.UserQueryManager;

import static scotty.common.Config.SCOTTY_APP_SECRET;
import static scotty.common.Config.SCOTTY_MESSENGER_TOKEN;
import static scotty.common.Config.SCOTTY_PAGE_ACCESS_TOKEN;

public class FacebookUtils {

    public static final MessengerSendClient SEND_CLIENT = MessengerPlatform.newSendClientBuilder(SCOTTY_PAGE_ACCESS_TOKEN).build();

    public static final MessengerReceiveClient RECEIVE_CLIENT = MessengerPlatform.newReceiveClientBuilder(SCOTTY_APP_SECRET, SCOTTY_MESSENGER_TOKEN)
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


}
