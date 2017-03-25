package scotty.common;

import java.util.List;

public class DialogInformation {

    private String userId;

    private List<DialogEntry> dialogHistory;

    public DialogInformation(List<DialogEntry> dialogHistory) {
        this.dialogHistory = dialogHistory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DialogEntry> getDialogHistory() {
        return dialogHistory;
    }

    public void setDialogHistory(List<DialogEntry> dialogHistory) {
        this.dialogHistory = dialogHistory;
    }
}
