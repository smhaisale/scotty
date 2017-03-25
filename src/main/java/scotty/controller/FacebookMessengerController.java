package scotty.controller;

import org.springframework.web.bind.annotation.*;
import static scotty.util.FacebookUtils.RECEIVE_CLIENT;

@RestController
@RequestMapping("/webhook")
public class FacebookMessengerController {

    @RequestMapping(method = {RequestMethod.GET})
    public String verify(@RequestParam(name = "hub.challenge", defaultValue = "") String challenge) {
        return challenge;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String message(@RequestBody String payload, @RequestHeader(name = "X-Hub-Signature") String signature) {
        new Thread( () -> {
            try {
                RECEIVE_CLIENT.processCallbackPayload(payload,signature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return "";  // Required to stop FB from resending the message over and over again.
    }
}