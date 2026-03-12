package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Driver ve wait objelerini oluşturur
    public BasePage() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Element görünür olana kadar bekler
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Element tıklanabilir olana kadar bekler
    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Tüm eşleşen elementleri bekleyip döner
    protected List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // Güvenli click işlemi yapar
    protected void click(By locator) {
        waitForClickability(locator).click();
    }

    // Input alanını temizleyip yazı yazar
    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Input alanına yazı ekler, clear yapmaz
    protected void typeWithoutClear(By locator, String text) {
        waitForVisibility(locator).sendKeys(text);
    }

    // ENTER tuşuna basar
    protected void pressEnter(By locator) {
        waitForVisibility(locator).sendKeys(Keys.ENTER);
    }

    // Elementin text değerini döner
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    // Element görüntüleniyor mu kontrol eder
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Url belirli bir ifade içerene kadar bekler
    protected void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }
    // Belirtilen saniye kadar bekler
    protected void waitForSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}