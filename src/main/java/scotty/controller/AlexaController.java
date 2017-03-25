package scotty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scotty.manager.DialogHistoryManager;
import scotty.manager.UserInformationManager;
import scotty.manager.UserQueryManager;

@RestController
@RequestMapping("/alexa")
public class AlexaController {

    private static final Integer ALEXA_TIMEOUT = 6000;

    @RequestMapping(method = {RequestMethod.GET})
    public String get(@RequestParam String amazonId, @RequestParam String query) {

        System.out.println("AlexaServlet: Received request from " + amazonId + ":\t" + query);

        String userId = UserInformationManager.getUserIdByAmazonId(amazonId);
        DialogHistoryManager.addEntry(userId, "alexa", query);

        String reply = UserQueryManager.getUnreviewedReply(userId, query, ALEXA_TIMEOUT);
        DialogHistoryManager.addEntry(userId, "system", reply);

        return reply;
    }
}