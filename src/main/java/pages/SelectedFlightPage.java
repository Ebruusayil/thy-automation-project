package pages;

import base.BasePage;
import org.openqa.selenium.By;

public class SelectedFlightPage extends BasePage {

    By footerDetailsContainer = By.cssSelector("div.av__style_footer-details-container__5ddsu");
    By routeInfo = By.xpath("//div[contains(@class,'av__style_item__Si1v7')]//span[contains(@class,'av__style_bullet-after__bK4T5')]");
    By dateInfo = By.xpath("//div[contains(@class,'av__style_item__Si1v7')]//span[contains(.,'Nis')]");
    By timeInfo = By.xpath("//div[contains(@class,'av__style_item__Si1v7')]//div[3]");
    By totalPriceText = By.xpath("//div[contains(.,'1 yolcu için toplam tutar')]");
    By continueButton = By.cssSelector("button[data-testid='availability-purchase-continue']");

    public boolean isPageOpened() {
        waitForSeconds(2);
        return isDisplayed(footerDetailsContainer);
    }

    public String getRouteInfo() {
        return getText(routeInfo);
    }

    public String getDateInfo() {
        return getText(dateInfo);
    }

    public String getTimeInfo() {
        return getText(timeInfo);
    }

    public boolean isTotalPriceDisplayed() {
        return isDisplayed(totalPriceText);
    }

    public boolean isContinueButtonDisplayed() {
        return isDisplayed(continueButton);
    }
}