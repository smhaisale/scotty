package scotty.common;

import scotty.util.MongoDb;

public class Config {

    public static final long WOZ_WAIT_TIMEOUT = 30000;

    public static final String SCOTTY_MESSENGER_TOKEN = "EAADrttEWnbcBAP2lQfTzHczj44fK0IEQSZCrxLTkMkWRgCfwn1Bu6vhh" +
            "ToqBZB7o12dmhyBQ3MDFMRzZCZB8dMe4V9FCnIZCHch2CMtGvcsKCvRz1IkKprPqkQHEYXZBdflCXAcFgYrqVTcj7BOBFyTw73ZCN" +
            "zgafTkFpkYJu270AZDZD";
    public static final String SCOTTY_APP_SECRET = "937c41b80beeaa91a423822d7523570f";
    public static final String SCOTTY_PAGE_ACCESS_TOKEN = "EAADrttEWnbcBAE3z1VNj2EBAZAozLC1ZBI9IZCCxxgxXxu4o8UYzDO" +
            "l1yF9D5zcAndZAtfWM3B8lywGKdXgq4ZBLc5qsB6i7gJCCeRnqJOQmrnYDOm1w6U0AlAF95vXzQpsmQghZCOUB2SXBFHijkNvRsk0" +
            "zOu1R2R1ai4rJqZBxAZDZD";

    public static final Integer CHATBOT_PORT = 2562;
    public static final String CHATBOT_ADDRESS = "http://localhost:" + CHATBOT_PORT + "/";

    public static final String BOT_URL = "http://67.21.5.21:5000/api/cmu_restaurant_system";

    public static final String WECHAT_APP_ID = "wx89ea916720689cb2";
    public static final String WECHAT_APP_SECRET = "da60df24df7a590503ae16dac4a5f709";

    public static final String MONGO_HOST = "54.175.153.240";
    public static final Integer MONGO_PORT = 27017;
    public static final String MONGO_DB_NAME = "scotty";

    public static final MongoDb DATABASE = new MongoDb(MONGO_HOST, MONGO_PORT, MONGO_DB_NAME);
}
