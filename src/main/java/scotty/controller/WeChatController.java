package scotty.controller;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.web.bind.annotation.*;
import scotty.manager.DialogHistoryManager;
import scotty.manager.UserInformationManager;
import scotty.manager.UserQueryManager;
import scotty.util.WeChatUtils;

import java.io.IOException;
import java.io.StringReader;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @RequestMapping(method = {RequestMethod.GET})
    public String verify(@RequestParam String echostr) {
        return echostr;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String message(@RequestBody String xml) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Element root = saxBuilder.build(new StringReader(xml)).getRootElement();

            String wechatId = root.getChild("FromUserName").getText();
            String text = root.getChild("Content").getText();

            new Thread( () -> {
                String userId = UserInformationManager.getUserIdByWechatId(wechatId);

                DialogHistoryManager.addEntry(userId, "wechat", text);

                String reply = UserQueryManager.getReviewedReply(userId, text);

                if (reply != null && !reply.equals("")) {
                    DialogHistoryManager.addEntry(userId, "system", reply);
                    WeChatUtils.sendMessage(wechatId, reply);
                }
            }).start();

        } catch (JDOMException | IOException e) {
            // handle Exception
        }

        return "";
    }

    public static void main(String[] args) {
        String xml = "<xml><ToUserName><![CDATA[gh_2519916abdfc]]></ToUserName>\n" +
                "<FromUserName><![CDATA[oUlcvw_00_8591IVhzpKecckInhI]]></FromUserName>\n" +
                "<CreateTime>1490408744</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[Abc]]></Content>\n" +
                "<MsgId>6401256813585382179</MsgId>\n" +
                "</xml>";

        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Element root = saxBuilder.build(new StringReader(xml)).getRootElement();

            String weChatUserId = root.getChild("FromUserName").getText();
            String query = root.getChild("Content").getText();

            System.out.println(weChatUserId + "\t" + query);
        } catch (JDOMException | IOException e) {
            // handle Exception
        }

    }

}
