package scotty.controller;

import org.springframework.web.bind.annotation.*;
import scotty.common.DialogReview;
import scotty.manager.DialogHistoryManager;
import scotty.manager.DialogReviewManager;
import scotty.manager.UserInformationManager;

import java.util.List;

import static scotty.servlet.MessengerServlet.SEND_CLIENT;

@RestController
@RequestMapping("/woz")
public class DialogReviewController {

    @RequestMapping(method = {RequestMethod.GET})
    public List<DialogReview> verify(@RequestParam(defaultValue = "") String userId,
                                     @RequestParam(defaultValue = "") String query,
                                     @RequestParam(defaultValue = "") String reply) {
        if (userId == null || userId.equals("")) {
            return DialogReviewManager.getAll();
        } else {

            DialogReview review = DialogReviewManager.get(userId);

            if (review != null && !review.getReviewed()) {
                DialogReviewManager.put(userId, query, reply);

                String facebookId = UserInformationManager.getFacebookId(userId);
                try {
                    SEND_CLIENT.sendTextMessage(facebookId, reply);
                    DialogHistoryManager.addEntry(userId, "system", reply);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public void message(@RequestParam String userId, @RequestParam String query, @RequestParam String reply) {

        DialogReviewManager.put(userId, query, reply);
    }

}
