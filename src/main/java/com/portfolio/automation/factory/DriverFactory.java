package com.portfolio.automation.factory;

import com.epam.healenium.SelfHealingDriver;
import com.portfolio.automation.utils.ConfigReader;
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
    private static SelfHealingDriver driver;  // Wrapped driver

    public static SelfHealingDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser");
            boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
            WebDriver baseDriver;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    if (headless) chromeOptions.addArguments("--headless");
                    baseDriver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--private");
                    if (headless) firefoxOptions.addArguments("--headless");
                    baseDriver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--inprivate");
                    edgeOptions.addArguments("--start-maximized");
                    if (headless) edgeOptions.addArguments("--headless");
                    baseDriver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            baseDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    Integer.parseInt(ConfigReader.getProperty("implicitWait"))
            ));

            baseDriver.manage().window().maximize();

            // Wrap the base driver with SelfHealingDriver
            driver = SelfHealingDriver.create(baseDriver);
        }
        return driver;
    }

    public static void resetDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}