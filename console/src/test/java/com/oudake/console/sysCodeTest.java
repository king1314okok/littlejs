package com.oudake.console;

import com.oudake.console.model.TblSysCode;
import com.oudake.console.service.SysCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class sysCodeTest {

    @Autowired
    private SysCodeService sysCodeService;

    @Test
    public void sysCodeTest() {
        TblSysCode sysCode = sysCodeService.findSysCodeByTypeAndName("SYS_CODE_STATUS", "NORMAL");
        System.out.println(sysCode.getDisplay1());
    }
}
