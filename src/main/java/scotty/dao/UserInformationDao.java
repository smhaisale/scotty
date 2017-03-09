package scotty.dao;

import scotty.common.UserInformation;
import scotty.util.MongoDbUtils;
import scotty.util.SystemUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Used to access all user information stored in this system (scotty).
 */
public class UserInformationDao {

    private static final boolean log = true;

    private static final String table = "user_information";

    private static final String USER_ID_FIELD = "user_id";
    private static final String FACEBOOK_USER_ID_FIELD = "facebook_user_id";
    private static final String AMAZON_USER_ID_FIELD = "amazon_user_id";
    private static final String SLACK_USER_ID_FIELD = "slack_user_id";

    private static MongoDbUtils database = new MongoDbUtils("localhost", 27017, "scotty");

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

    public static UserInformation create(String facebookId, String amazonId, String slackId) {

        String userId = UUID.randomUUID().toString();

        if (log) {
            SystemUtils.log(UserInformationDao.class, "Create user information for new user " + userId);
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
            SystemUtils.log(UserInformationDao.class, "Get user information for " + userId);
        }

        Map<String, String> map = database.findOne(table, userId);

        return fromMap(map);
    }

    public static UserInformation getByFacebookId(String facebookId) {
        Map map = database.findOne(table, FACEBOOK_USER_ID_FIELD, facebookId);
        return fromMap(map);
    }

    public static void put(UserInformation information) {

        String userId = information.getUserId();

        if (log) {
            SystemUtils.log(UserInformationDao.class, "Put user information " + information + " for " + userId);
        }

        database.insert(table, information.getUserId(), toMap(information));
    }
}
