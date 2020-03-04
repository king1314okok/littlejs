package com.oudake.web;

import com.oudake.common.msg.MailMsg;
import com.oudake.web.mq.CoreSender;
import com.oudake.web.service.GatewayOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class rabbitTest {

    @Autowired
    private CoreSender coreSender;
    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Test
    public void sendAndReceiveTest() {

    }

    @Test
    public void sendMailTest() {
        MailMsg mailMsg = new MailMsg();
        mailMsg.setToAddr("805922010@qq.com");
        mailMsg.setTitle("这是title");
        mailMsg.setContext("这是第一行<br>这是第二行");
        mailMsg.setHtml(true);
        coreSender.sendMail(mailMsg);
    }

}
