package scotty.servlet;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import scotty.common.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static scotty.common.Config.*;

public class WebhookServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getQueryString());
        System.out.println(request.getRequestURL());
        System.out.println(request.getPathTranslated());

        for(String param: request.getParameterMap().keySet()) {
            System.out.println(param + "\t" + request.getParameter(param));
        }

        String token = request.getParameter("hub.verify_token");
        //if (token != null && token.equals(SCOTTY_FB_TOKEN))
        HttpUtils.writeText(response, request.getParameter("hub.challenge"));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String payload = HttpUtils.getRequestBody(request);
        String signature = request.getHeader("X-Hub-Signature");

        try {
            MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(SCOTTY_PAGE_ACCESS_TOKEN).build();

            MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder(SCOTTY_APP_SECRET, SCOTTY_MESSENGER_TOKEN)
                    .onTextMessageEvent(event ->  {
                        try {
                            sendClient.sendTextMessage(event.getSender().getId(), event.getText());
                        } catch (Exception e) {
                            ;//
                        }
                    })
                    .build();

            receiveClient.processCallbackPayload(payload, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
