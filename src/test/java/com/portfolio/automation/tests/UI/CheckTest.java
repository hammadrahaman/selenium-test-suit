package com.portfolio.automation.tests.UI;

import com.portfolio.automation.base.BaseTest;
import com.portfolio.automation.base.LoginPage;
import org.testng.annotations.Test;

public class CheckTest extends BaseTest {

    @Test
    public void loginEstesQA() {
        LoginPage login = new LoginPage(driver);
         login.login("estes_qa");

    }

    @Test
    public void loginSaiaQA() {
        LoginPage login = new LoginPage(driver);
         login.login("saia_qa");
    }

    @Test
    public void loginTforceQA() {
        LoginPage login = new LoginPage(driver);
         login.login("tforce_qa");
    }

    @Test
    public void loginSeflQA() {
        LoginPage login = new LoginPage(driver);
        login.login("sefl_qa");
    }
}
