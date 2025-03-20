package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class LambdaTestDemo {
    String username = "kypase184884"; // Thay bằng username của bạn
    String accessKey = "LT_8YRI6D82FQKVc5sp1bibS1CFDQYwxmtuLts2aF45yykRp7E"; // Thay bằng access key của bạn
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");

        String hubURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
        driver = new RemoteWebDriver(new URL(hubURL), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testSearchPlayToggleFullScreenAndReturnHome() throws InterruptedException {
        // 1. Mở trang chủ YouTube
        driver.get("https://www.youtube.com");

        // 2. Tìm ô tìm kiếm và nhập từ khóa "LambdaTest tutorial", sau đó gửi phím ENTER
        WebElement searchBox;
        try {
            searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search_query")));
        } catch (Exception e) {
            // Nếu không tìm thấy theo name, thử theo css selector
            searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input#search")));
        }
        searchBox.click();
        searchBox.sendKeys("LambdaTest tutorial");
        searchBox.sendKeys(Keys.RETURN);

        // 3. Đợi kết quả tìm kiếm xuất hiện và click vào video đầu tiên
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("video-title")));
        List<WebElement> videoResults = driver.findElements(By.id("video-title"));
        Assert.assertFalse(videoResults.isEmpty(), "❌ Không tìm thấy kết quả video!");
        videoResults.get(0).click();
        System.out.println("✅ Video đã bắt đầu phát!");

        // 4. Chuyển sang chế độ toàn màn hình
        WebElement fullScreenButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ytp-fullscreen-button")));
        fullScreenButton.click();
        System.out.println("📺 Đã chuyển sang chế độ toàn màn hình!");
        Thread.sleep(3000); // Chờ 3 giây ở chế độ toàn màn hình

        // 5. Thoát khỏi chế độ toàn màn hình bằng phím ESC
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.ESCAPE);
        System.out.println("🔳 Đã thoát chế độ toàn màn hình!");
        Thread.sleep(2000);

        // 6. Quay lại trang chủ YouTube
        driver.get("https://www.youtube.com");
        System.out.println("🏠 Đã quay lại trang chủ YouTube!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
