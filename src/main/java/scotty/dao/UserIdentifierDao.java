package scotty.dao;

import scotty.common.UserInformation;
import scotty.util.MongoDb;
import scotty.util.SystemUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Used to access all user information stored in this system (scotty).
 */
public class UserIdentifierDao {

    private static final boolean log = true;

    private static final String table = "user_information";

    private static final String USER_ID_FIELD = "user_id";
    private static final String FACEBOOK_USER_ID_FIELD = "facebook_user_id";
    private static final String AMAZON_USER_ID_FIELD = "amazon_user_id";
    private static final String SLACK_USER_ID_FIELD = "slack_user_id";
    private static final String WECHAT_USER_ID_FIELD = "wechat_user_id";

    private static MongoDb database = new MongoDb("54.175.153.240", 27017, "scotty");

    private static UserInformation fromMap(Map map) {

        if (map == null || map.get(USER_ID_FIELD) == null) {
            return null;
        }

        UserInformation information = new UserInformation();

        information.setUserId((String) map.get(USER_ID_FIELD));
        information.setFacebookUserId((String) map.get(FACEBOOK_USER_ID_FIELD));
        information.setAmazonUserId((String) map.get(AMAZON_USER_ID_FIELD));
        information.setSlackUserId((String) map.get(SLACK_USER_ID_FIELD));

        return information;
    }

    private static Map toMap(UserInformation information) {

        if (information == null || information.getUserId() == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        map.put(USER_ID_FIELD, information.getUserId());
        map.put(FACEBOOK_USER_ID_FIELD, information.getFacebookUserId());
        map.put(AMAZON_USER_ID_FIELD, information.getAmazonUserId());
        map.put(SLACK_USER_ID_FIELD, information.getSlackUserId());

        return map;
    }

    public static UserInformation create() {
        String userId = UUID.randomUUID().toString();

        if (log) {
            SystemUtils.log("UserIdentifierDao", "Create user information for new user " + userId);
        }

        UserInformation user = new UserInformation();
        user.setUserId(userId);
        put(user);

        return user;
    }

    public static UserInformation create(String facebookId, String amazonId, String slackId) {

        String userId = UUID.randomUUID().toString();

        if (log) {
            SystemUtils.log("UserIdentifierDao", "Create user information for new user " + userId);
        }

        UserInformation user = new UserInformation();

        user.setUserId(userId);
        user.setFacebookUserId(facebookId);
        user.setAmazonUserId(amazonId);
        user.setSlackUserId(slackId);

        put(user);

        return user;
    }

    public static UserInformation get(String userId) {

        if (log) {
            SystemUtils.log("UserIdentifierDao", "Get user information for " + userId);
        }

        Map<String, String> map = database.findOne(table, userId);

        return fromMap(map);
    }

    public static UserInformation getByFacebookId(String facebookId) {
        Map map = database.findOne(table, FACEBOOK_USER_ID_FIELD, facebookId);
        return fromMap(map);
    }

    public static UserInformation getByAmazonId(String amazonId) {
        Map map = database.findOne(table, AMAZON_USER_ID_FIELD, amazonId);
        return fromMap(map);
    }

    public static UserInformation getByWechatId(String wechatId) {
        Map map = database.findOne(table, WECHAT_USER_ID_FIELD, wechatId);
        return fromMap(map);
    }

    public static void put(UserInformation information) {

        String userId = information.getUserId();

        if (log) {
            SystemUtils.log("UserIdentifierDao", "Put user information " + information + " for " + userId);
        }

        database.insert(table, information.getUserId(), toMap(information));
    }

    public static void main(String[] args) {
        UserInformation user1 = create("facebook1", "amazon", "slack");
        UserInformation user2 = create("facebook2", null, null);
        UserInformation user3 = create(null, null, null);

        System.out.println(get(user1.getUserId()));
        System.out.println(get(user2.getUserId()));
        System.out.println(get(user3.getUserId()));

        System.out.println(getByFacebookId("facebook2"));
        System.out.println(getByAmazonId("amazon"));
    }
}
