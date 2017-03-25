package scotty.common;

import java.io.Serializable;
import java.util.Date;

public class DialogEntry implements Serializable {

    private static long serialVersionUID = 89435239460l;

    private Date timestamp;

    private String source;

    private String text;

    private Integer turn;

    public DialogEntry() {

    }

    public DialogEntry(String source, String text) {
        this.timestamp = new Date();
        this.source = source;
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "\nDialogEntry{" +
                "source='" + source + '\'' +
                ", text='" + text + '\'' +
                ", turn=" + turn +
                "}";
    }
}
