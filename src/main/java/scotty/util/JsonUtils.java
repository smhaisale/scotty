package scotty.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scotty.dao.ChatbotServiceDao;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static String createJSON(String key, String value) {
        return "{\"" + key + "\":\"" + value + "\"}";
    }

    public static Map<String, Object> processJSON(String json, List<String> fields) {

        JSONParser parser = new JSONParser();
        Object obj = new Object();

        try {
            obj = parser.parse(json);
        } catch (ParseException e) {
            throw new JsonParseException("Error processing JSON string: " + json);
        }

        Map<String, Object> map = (Map) obj;

        return map;
    }

    public static void main(String[] args) {
        String json = ChatbotServiceDao.mockJsonCall();

        Map<String, Object> fields = processJSON(json, Lists.newArrayList("sys_res", "dialog_state"));

        for(String x : fields.keySet()) {
            System.out.println(x + "\t" + fields.get(x));
        }
    }
}
