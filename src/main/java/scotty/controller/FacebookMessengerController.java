package scotty.controller;

import org.springframework.web.bind.annotation.*;
import static scotty.servlet.MessengerServlet.RECEIVE_CLIENT;

@RestController
@RequestMapping("/webhook")
public class FacebookMessengerController {

    @RequestMapping(method = {RequestMethod.GET})
    public String verify(@RequestParam(name = "hub.challenge") String challenge) {
        return challenge;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public void message(@RequestBody String payload, @RequestHeader(name = "X-Hub-Signature") String signature) {

        try {
            RECEIVE_CLIENT.processCallbackPayload(payload, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}