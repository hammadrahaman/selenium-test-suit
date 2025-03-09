package com.portfolio.Automation.factory;

import com.portfolio.Automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser");

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized"); // Start maximized
                    chromeOptions.addArguments("--disable-popup-blocking"); // Disable popups
                    chromeOptions.addArguments("--headless"); // Run in headless mode (optional)
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--private"); // Open in private mode
                    firefoxOptions.addArguments("--headless"); // Run headless mode
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--inprivate"); // Open in InPrivate mode
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    Integer.parseInt(ConfigReader.getProperty("implicitWait"))
            ));
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}