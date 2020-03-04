package com.oudake.console;

import com.oudake.common.ResultEntity;
import com.oudake.console.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {

    @Autowired
    private MailService mailService;

    @Test
    public void mailTest() {
        String title = "";
        String context = "<html><body><table border=\"1\"><tr><th>Month</th><th>Savings</th></tr><tr><td>January</td><td>$100</td></tr></table></body></html>";
        String toAddr = "805922010@qq.com";
        ResultEntity result = mailService.sendHtmlMail(title, context, toAddr);
        System.out.println(result.getMsg());
    }
}
