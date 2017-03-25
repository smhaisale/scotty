package scotty.manager;

import scotty.common.ChatbotReply;
import scotty.common.DialogReview;
import scotty.dao.ChatbotServiceDao;
import scotty.dao.DialogStateDao;
import scotty.util.SystemUtils;

import java.util.List;

public class UserQueryManager {

    private static final Boolean log = true;

    public static List<String> getReply(String userId, String query) {

        if (log) {
            SystemUtils.log("UserQueryManager", "Getting reply for " + userId + " : " + query);
        }

        ChatbotReply reply = ChatbotServiceDao.getReply(userId, query, DialogStateDao.get(userId));

        DialogStateDao.put(userId, reply.getState());

        return reply.getResponses();
    }

    public static String getUnreviewedReply(String userId, String query, Integer timeout) {

        return DialogReviewManager.review(userId, query, getReply(userId, query), timeout).getSelectedResponse();
    }

    public static String getReviewedReply(String userId, String query) {

        DialogReview review = DialogReviewManager.review(userId, query, getReply(userId, query), null);
        return review.getSelectedResponse();
    }
}
