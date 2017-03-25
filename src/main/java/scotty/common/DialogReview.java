package scotty.common;

import java.util.List;

public class DialogReview {

    private String userId;

    private String query;

    private List<String> responses;

    private String selectedResponse;

    private Boolean isReviewed;

    private DialogInformation dialogInformation;

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

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
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

    public DialogInformation getDialogInformation() {
        return dialogInformation;
    }

    public void setDialogInformation(DialogInformation dialogInformation) {
        this.dialogInformation = dialogInformation;
    }

    @Override
    public String toString() {
        return "DialogReview{" +
                "userId='" + userId + '\'' +
                ", query='" + query + '\'' +
                ", responses=" + responses +
                ", selectedResponse='" + selectedResponse + '\'' +
                ", isReviewed=" + isReviewed +
                ", dialogInformation=" + dialogInformation +
                '}';
    }
}
