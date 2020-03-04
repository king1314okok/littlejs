package com.oudake.core;

import com.oudake.core.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void mailTest() {
        mailService.sendMail("759046694@qq.com", "这是标题", "这是内容", true);
    }
}
