package scotty.database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//TODO: Extend as interface to database (REDIS?)
public class WozReview {

    private static Map<String, String> pendingReviews = new ConcurrentHashMap<String, String>();

    public static void put(String query, String answer) {
        pendingReviews.put(query, answer);
    }

    public static String get(String query) {
        return pendingReviews.get(query);
    }

    public static Map<String, String> get() {
        return pendingReviews;
    }

    public static String delete(String query) {
        String answer = pendingReviews.get(query);
        pendingReviews.remove(query);
        return answer;
    }

}
