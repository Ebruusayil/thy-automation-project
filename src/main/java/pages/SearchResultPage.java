package pages;

import base.BasePage;
import models.FlightInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchResultPage extends BasePage {

    By flightSelectionBreadcrumb = By.xpath("//b[contains(.,'Uçuş seçimi')]");
    By outboundBadge = By.id("outbound-title-id");
    By flightTitle = By.xpath("//h4[contains(@class,'av__style_title')]");

    By firstFlightCard = By.xpath("(//div[contains(@id,'flightItem_recommendedOrder_0_')])[1]");

    public boolean isFlightSelectionPageOpened() {
        waitForUrlContains("availability");
        return isDisplayed(flightSelectionBreadcrumb);
    }

    public String getBreadcrumbText() {
        return getText(flightSelectionBreadcrumb);
    }

    public String getOutboundBadgeText() {
        return getText(outboundBadge);
    }

    public String getFlightTitleText() {
        return getText(flightTitle);
    }

    public FlightInfo getFirstFlightInfo() {
        WebElement card = waitForVisibility(firstFlightCard);

        FlightInfo info = new FlightInfo();

        info.setDate("15 Nisan");

        info.setDepartureTime(
                card.findElement(By.xpath(".//div[contains(@class,'av__style_origin___9we8')]//div[contains(@class,'av__style_time__3PSjQ')]"))
                        .getText()
        );

        info.setArrivalTime(
                card.findElement(By.xpath(".//div[contains(@class,'av__style_destination__S1yE0')]//div[contains(@class,'av__style_time__3PSjQ')]"))
                        .getText()
        );

        info.setOriginAirport(
                card.findElement(By.xpath(".//div[contains(@class,'av__style_origin___9we8')]//div[contains(@class,'av__style_port__oHYAj')]"))
                        .getText()
        );

        info.setDestinationAirport(
                card.findElement(By.xpath(".//div[contains(@class,'av__style_destination__S1yE0')]//div[contains(@class,'av__style_port__oHYAj')]"))
                        .getText()
        );

        info.setTransferType(
                card.findElement(By.xpath(".//span[contains(@class,'av__style_transfer-port__QLQ0Z')]"))
                        .getText()
        );

        info.setBusinessPrice(
                normalizePrice(
                        card.findElement(By.xpath(".//div[contains(@class,'av__style_bus-title__nTkqz')]/ancestor::div[contains(@role,'button')]//span[contains(@class,'av__style_pricePart__lYxno')]"))
                                .getText(),
                        card.findElement(By.xpath(".//div[contains(@class,'av__style_bus-title__nTkqz')]/ancestor::div[contains(@role,'button')]//span[contains(@class,'av__style_priceDecimal__jJ4Hp')]"))
                                .getText()
                )
        );

        return info;
    }

    public void openFirstFlightDetails() {
        WebElement card = waitForVisibility(firstFlightCard);
        WebElement detailButton = card.findElement(By.xpath(".//div[contains(@class,'av__style_flight-detail-title___37OQ')]"));
        detailButton.click();
    }

    public void openFirstFlightBusinessArea() {
        WebElement card = waitForVisibility(firstFlightCard);
        WebElement businessArea = card.findElement(
                By.xpath(".//div[contains(@class,'av__style_bus-title__nTkqz')]/ancestor::div[contains(@role,'button')]")
        );
        businessArea.click();
    }

    public String getExpandedFlightAirline() {
        return waitForVisibility(
                By.xpath("//div[contains(.,'Hava yolu - Uçuş no:')]/following-sibling::div")
        ).getText();
    }

    public String getExpandedFlightDepartureAirport() {
        return waitForVisibility(
                By.xpath("(//div[contains(@class,'av__style_name__') and contains(.,'Havalimanı')])[1]")
        ).getText();
    }

    public String getExpandedFlightArrivalAirport() {
        return waitForVisibility(
                By.xpath("(//div[contains(@class,'av__style_name__') and contains(.,'Havalimanı')])[2]")
        ).getText();
    }

    public String getBusinessFlyPrice() {
        String part = waitForVisibility(
                By.xpath("//div[@id='BF']//button[contains(@aria-label,'BusinessFly')]//span[contains(@class,'av__style_pricePart__lYxno')]")
        ).getText();

        String decimal = waitForVisibility(
                By.xpath("//div[@id='BF']//button[contains(@aria-label,'BusinessFly')]//span[contains(@class,'av__style_priceDecimal__jJ4Hp')]")
        ).getText();

        return normalizePrice(part, decimal);
    }

    public void selectBusinessFlyPackage() {
        click(By.xpath("//div[@id='BF']//button[contains(@aria-label,'BusinessFly')]"));
    }

    private String normalizePrice(String part, String decimal) {
        String cleanedPart = part.replace(".", "").trim();
        String cleanedDecimal = decimal.replace(",", "").trim();
        return cleanedPart + "." + cleanedDecimal;
    }
}