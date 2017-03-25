package scotty.controller;

import org.springframework.web.bind.annotation.*;
import scotty.common.DialogReview;
import scotty.common.UserInformation;
import scotty.dao.UserIdentifierDao;
import scotty.manager.DialogHistoryManager;
import scotty.manager.DialogReviewManager;
import scotty.util.WeChatUtils;

import java.util.List;

import static scotty.util.FacebookUtils.SEND_CLIENT;

@CrossOrigin
@RestController
@RequestMapping("/woz")
public class DialogReviewController {

    @RequestMapping(method = {RequestMethod.GET})
    public List<DialogReview> verify(@RequestParam(defaultValue = "") String userId,
                                     @RequestParam(defaultValue = "") String query,
                                     @RequestParam(defaultValue = "") String response) {
        if (userId == null || userId.equals("")) {
            return DialogReviewManager.getAll();
        } else {

            DialogReview review = DialogReviewManager.get(userId);

            if (review != null && !review.getReviewed()) {
                DialogReviewManager.put(userId, query, response);

                UserInformation user = UserIdentifierDao.get(userId);

                try {
                    if (user.getFacebookUserId() != null) {
                        System.out.println("Sending fb message " + response + " to " + user.getFacebookUserId());
                        SEND_CLIENT.sendTextMessage(user.getFacebookUserId(), response);
                    } else if (user.getWechatUserId() != null) {
                        System.out.println("Sending wechat message " + response + " to " + user.getWechatUserId());
                        WeChatUtils.sendMessage(user.getWechatUserId(), response);
                    }
                    DialogHistoryManager.addEntry(userId, "woz", response);
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
