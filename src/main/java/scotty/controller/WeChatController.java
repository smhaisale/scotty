package scotty.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @RequestMapping(method = {RequestMethod.GET})
    public String verify(@RequestParam String echostr) {
        return echostr;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String message(@RequestBody String body) {
        System.out.println(body);
        return "Received.";
    }


}
