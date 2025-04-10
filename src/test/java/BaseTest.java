import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/yandexdriver");
        //браузер: https://repo.yandex.ru/yandex-browser/deb/pool/main/y/yandex-browser-stable/, 25.2.1.939
        File yandex = Paths.get("/opt/yandex/browser/yandex-browser").toFile();
        driver = new ChromeDriver(chromeOptions(yandex));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static ChromeOptions chromeOptions(File browser) {
        ChromeOptions options = new ChromeOptions();
        Path profileDir = Paths.get(System.getProperty("user.dir"), "target", "profile");
        try {
            if (!Files.exists(profileDir)) {
                Files.createDirectories(profileDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize profile directory", e);
        }
        //опция необходима для обхода капчи
        options.addArguments("--user-data-dir=" + System.getProperty("user.dir") + "/target/profile");
        options.setBinary(browser);
        return options;
    }
}