package scotty.common;

import java.util.HashMap;
import java.util.Map;

public class WozReview {

    private String userId;

    private String query;

    private Map<String, Integer> responses;

    private String selectedResponse;

    private Boolean isReviewed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, Integer> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Integer> responses) {
        this.responses = responses;
    }

    public String getSelectedResponse() {
        return selectedResponse;
    }

    public void setSelectedResponse(String selectedResponse) {
        this.selectedResponse = selectedResponse;
    }

    public Boolean getReviewed() {
        return isReviewed;
    }

    public void setReviewed(Boolean reviewed) {
        isReviewed = reviewed;
    }

    @Override
    public String toString() {
        return "WozReview{" +
                "userId='" + userId + '\'' +
                ", query='" + query + '\'' +
                ", responses=" + responses +
                ", selectedResponse='" + selectedResponse + '\'' +
                ", isReviewed=" + isReviewed +
                '}';
    }
}
