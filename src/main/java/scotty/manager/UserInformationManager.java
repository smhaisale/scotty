package scotty.manager;

import scotty.common.UserInformation;
import scotty.dao.UserIdentifierDao;

//TODO: Define and write edge cases for this class
public class UserInformationManager {

    private static final Boolean log = true;

    public static String getFacebookId(String userId) {
        UserInformation identifier = UserIdentifierDao.get(userId);

        if (identifier == null) {
            return null;
        }

        return identifier.getFacebookUserId();
    }

    public static String getUserIdByFacebookId(String facebookId) {
        UserInformation user = UserIdentifierDao.getByFacebookId(facebookId);

        if (user == null) {
            user = UserIdentifierDao.create(facebookId, null, null);
        }

        return user.getUserId();
    }

    public static String getUserIdByAmazonId(String amazonId) {
        UserInformation user = UserIdentifierDao.getByAmazonId(amazonId);

        if (user == null) {
            user = UserIdentifierDao.create(null, amazonId, null);
        }

        return user.getUserId();
    }
}
