package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (driver.get() == null) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // Toggle headless using Maven flag
            // mvn test              -> headless=true
            // mvn test -Dheadless=false -> headed
            String headless = System.getProperty("headless", "true");

            if (headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless=new"); // Chrome 109+
                options.addArguments("--window-size=1920,1080");
            }

            // Stability flags (VERY IMPORTANT for CI)
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver.set(new ChromeDriver(options));
        }

        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

