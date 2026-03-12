package pages;

import base.BasePage;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    By cookieAccept = By.xpath("//button[contains(.,'Tüm çerezleri kabul ediyorum')]");
    By oneWayButton = By.id("one-way");
    By toInput = By.id("toPort");
    By londonOption = By.xpath("//ul[@id='bookerInputList']//li[@aria-label='Londra']");
    By april15 = By.xpath("//abbr[contains(@aria-label,'Nisan 15')]/parent::button");
    By confirmDate = By.xpath("//button[contains(.,'Tamam')]");
    By searchButton = By.xpath("//button[contains(.,'Uçuş ara')]");

    public void navigateToWebsite() {
        driver.get("https://www.turkishairlines.com/tr-tr");
        waitForSeconds(2);
        click(cookieAccept);
    }

    public void selectOneWay() {
        click(oneWayButton);
    }
    public void enterToCity(String city) {
        type(toInput, city);
        click(londonOption);
        waitForSeconds(1);
    }
    public void selectDate() {
        click(april15);
        click(confirmDate);
    }

    public void clickSearch() {
        click(searchButton);
    }

    public void searchOneWayFlight(String to) {
        selectOneWay();
        enterToCity(to);
        selectDate();
        clickSearch();
    }
}