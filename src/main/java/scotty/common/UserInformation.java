package scotty.common;

/**
 * Concept of a user in the scotty system.
 */
public class UserInformation {

    private String userId;
    private String facebookUserId;
    private String amazonUserId;
    private String slackUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public String getAmazonUserId() {
        return amazonUserId;
    }

    public void setAmazonUserId(String amazonUserId) {
        this.amazonUserId = amazonUserId;
    }

    public String getSlackUserId() {
        return slackUserId;
    }

    public void setSlackUserId(String slackUserId) {
        this.slackUserId = slackUserId;
    }
}
