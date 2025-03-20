package com.portfolio.automation.base;

import com.portfolio.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    String username;
    String password;

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        username = ConfigReader.getProperty("username");
        password = ConfigReader.getProperty("password");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath=("//*[@id='kc-text']"))
    WebElement contactsupport;

    private By optymTeamLogin = By.xpath("//*[text()='Optym Team Login']");
    private By emailField = By.xpath("//*[@placeholder='Email, phone, or Skype']");
    private By passwordField = By.xpath("//*[@type='password']");
    private By submitSignIn = By.xpath("//*[@type='submit']");

    private By contactSupport = By.xpath("//*[@id='kc-text']");


    public void waitMethod(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            System.out.println("Click failed: Retrying...");
            try {
                Thread.sleep(1000); // Small delay before retrying
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            } catch (Exception retryException) {
                throw new RuntimeException("Failed to click the element: " + locator, retryException);
            }
        }
    }

    public void sendKeys(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear(); // Clear the field before typing (optional)
            element.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Typing failed: Retrying...");
            try {
                Thread.sleep(1000); // Small delay before retrying
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.clear();
                element.sendKeys(text);
            } catch (Exception retryException) {
                throw new RuntimeException("Failed to enter text in element: " + locator, retryException);
            }
        }
    }


    public void login(String env){
        driver.navigate().to(ConfigReader.getProperty(env));
        System.out.println("------------------");
        waitMethod(optymTeamLogin);
        click(optymTeamLogin);
        waitMethod(emailField);
        System.out.println("The user name ==> "+username);
        sendKeys(emailField, username);
        waitMethod(submitSignIn);
        click(submitSignIn);
        waitMethod(passwordField);
        System.out.println("The password  ==> "+password);
        sendKeys(passwordField, password);
        click(submitSignIn);
    }


    public void visitWebPage(){
        driver.navigate().to(ConfigReader.getProperty("webpage"));
    }



}
