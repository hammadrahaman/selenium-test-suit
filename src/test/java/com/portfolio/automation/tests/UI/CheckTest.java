package com.portfolio.automation.tests.UI;

import com.portfolio.automation.base.BaseTest;
import com.portfolio.automation.base.LoginPage;
import org.testng.annotations.Test;

public class CheckTest extends BaseTest {

    @Test
   public void visitPage(){
        LoginPage log = new LoginPage(driver);
        log.visitWebPage();
    }
}
