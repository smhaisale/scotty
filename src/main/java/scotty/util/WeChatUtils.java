package scotty.util;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static scotty.common.Config.WECHAT_APP_ID;
import static scotty.common.Config.WECHAT_APP_SECRET;

public class WeChatUtils {

    private static final String ACCESS_TOKEN_URL = "https://api.wechat.com/cgi-bin/token?grant_type=client_credential";
    private static final String SEND_MESSAGE_URL = "https://api.wechat.com/cgi-bin/message/custom/send?access_token=";

    private static final String USER_ID_FIELD = "touser";
    private static final String MSG_TYPE_FIELD = "msgtype";
    private static final String CONTENT_FIELD = "content";

    public static String getAccessToken() {
        String url = ACCESS_TOKEN_URL + "&appid=" + WECHAT_APP_ID + "&secret=" + WECHAT_APP_SECRET;
        String accessToken = "";
        try {
            String json = HttpUtils.doGet(url);
            accessToken = (String) JsonUtils.processJSON(json, null).get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String sendMessage(String wechatId, String text) {

        if (text == null || text.equals("")) {
            return "";
        }

        String url = SEND_MESSAGE_URL + getAccessToken();

        JSONObject content = new JSONObject();
        content.put(CONTENT_FIELD, text);
        JSONObject json = new JSONObject();
        json.put(USER_ID_FIELD, wechatId);
        json.put(MSG_TYPE_FIELD, "text");
        json.put("text", content);

        return HttpUtils.doPostWithParams(url, json.toJSONString());
    }

    public static void main(String[] args) {
        System.out.println(sendMessage("oUlcvw_00_8591IVhzpKecckInhI", "hello"));
    }

}
