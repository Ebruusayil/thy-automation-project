package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.FlightInfo;
import org.testng.Assert;
import pages.HomePage;
import pages.SearchResultPage;
import pages.SelectedFlightPage;

public class FlightSearchSteps {

    HomePage homePage = new HomePage();
    SearchResultPage searchResultPage = new SearchResultPage();
    SelectedFlightPage selectedFlightPage = new SelectedFlightPage();

    FlightInfo storedFlightInfo;

    @Given("user navigates to Turkish Airlines website")
    public void goToWebsite() {
        System.out.println("==================================================");
        System.out.println("STEP: Kullanıcı THY sitesine gidiyor");
        homePage.navigateToWebsite();
        System.out.println("RESULT: THY sitesi açıldı");
        System.out.println("==================================================");
    }

    @When("user searches one way flight from Istanbul to London for April 15")
    public void searchFlight() {
        System.out.println("STEP: Tek yön uçuş araması yapılıyor");
        System.out.println("EXPECTED SEARCH DATA:");
        System.out.println("From: Istanbul");
        System.out.println("To: Londra");
        System.out.println("Date: 15 Nisan");
        homePage.searchOneWayFlight("Londra");
        System.out.println("RESULT: Arama yapıldı");
        System.out.println("==================================================");
    }

    @Then("flight selection page should be opened")
    public void verifyResults() {
        System.out.println("STEP: Uçuş seçim sayfasının açıldığı kontrol ediliyor");

        boolean isOpened = searchResultPage.isFlightSelectionPageOpened();

        System.out.println("EXPECTED: availability içeren uçuş seçim sayfası açılmalı");
        System.out.println("ACTUAL: " + isOpened);

        Assert.assertTrue(
                isOpened,
                "Uçuş seçim sayfası açılmadı."
        );

        System.out.println("RESULT: Uçuş seçim sayfası başarıyla açıldı");
        System.out.println("==================================================");
    }

    @Then("selected flight information should be correct")
    public void selectedFlightInformationShouldBeCorrect() {

        System.out.println("STEP: Sonuç sayfasındaki üst bilgi doğrulanıyor");

        String actualBreadcrumb = searchResultPage.getBreadcrumbText();
        String actualOutboundBadge = searchResultPage.getOutboundBadgeText();
        String actualTitle = searchResultPage.getFlightTitleText();

        System.out.println("EXPECTED Breadcrumb: Uçuş seçimi");
        System.out.println("ACTUAL Breadcrumb: " + actualBreadcrumb);
        Assert.assertEquals(
                actualBreadcrumb,
                "Uçuş seçimi",
                "Breadcrumb değeri hatalı."
        );

        System.out.println("EXPECTED Badge: GİDİŞ");
        System.out.println("ACTUAL Badge: " + actualOutboundBadge);
        Assert.assertEquals(
                actualOutboundBadge,
                "GİDİŞ",
                "Tek yön seçiminde GİDİŞ badge'i görünmüyor."
        );

        System.out.println("EXPECTED Route in Title: İstanbul - Londra");
        System.out.println("EXPECTED Date in Title: 15 Nisan");
        System.out.println("ACTUAL Title: " + actualTitle);

        Assert.assertTrue(
                actualTitle.contains("İstanbul - Londra"),
                "Rota bilgisi hatalı. Actual: " + actualTitle
        );

        Assert.assertTrue(
                actualTitle.contains("15 Nisan"),
                "Tarih bilgisi hatalı. Actual: " + actualTitle
        );

        System.out.println("RESULT: Üst bilgi doğrulaması başarılı");
        System.out.println("==================================================");
    }

    @When("user stores first flight data from the first result")
    public void userStoresFirstFlightDataFromTheFirstResult() {
        System.out.println("STEP: İlk uçuş kartındaki veriler alınıyor ve saklanıyor");

        storedFlightInfo = searchResultPage.getFirstFlightInfo();

        System.out.println("STORED FLIGHT INFO:");
        System.out.println("Date: " + storedFlightInfo.getDate());
        System.out.println("Departure Time: " + storedFlightInfo.getDepartureTime());
        System.out.println("Arrival Time: " + storedFlightInfo.getArrivalTime());
        System.out.println("Origin Airport: " + storedFlightInfo.getOriginAirport());
        System.out.println("Destination Airport: " + storedFlightInfo.getDestinationAirport());
        System.out.println("Transfer Type: " + storedFlightInfo.getTransferType());
        System.out.println("Business Price: " + storedFlightInfo.getBusinessPrice());
        System.out.println("==================================================");
    }

    @When("user opens first flight details")
    public void userOpensFirstFlightDetails() {
        System.out.println("STEP: İlk uçuşun seyahat detayları açılıyor");
        searchResultPage.openFirstFlightDetails();
        System.out.println("RESULT: Seyahat detayları açıldı");
        System.out.println("==================================================");
    }

    @Then("expanded flight details should match stored values")
    public void expandedFlightDetailsShouldMatchStoredValues() {

        System.out.println("STEP: Açılan uçuş detayları saklanan veriler ile karşılaştırılıyor");

        String airlineText = searchResultPage.getExpandedFlightAirline();
        String departureAirportText = searchResultPage.getExpandedFlightDepartureAirport();
        String arrivalAirportText = searchResultPage.getExpandedFlightArrivalAirport();

        System.out.println("EXPECTED Airline contains: Türk Hava Yolları");
        System.out.println("ACTUAL Airline: " + airlineText);
        Assert.assertTrue(
                airlineText.contains("Türk Hava Yolları"),
                "Airline bilgisi eşleşmedi. Actual: " + airlineText
        );

        System.out.println("EXPECTED Departure Airport contains: " + storedFlightInfo.getOriginAirport());
        System.out.println("ACTUAL Departure Airport: " + departureAirportText);
        Assert.assertTrue(
                departureAirportText.contains(storedFlightInfo.getOriginAirport()),
                "Kalkış airport bilgisi eşleşmedi. Actual: " + departureAirportText
        );

        System.out.println("EXPECTED Arrival Airport contains: " + storedFlightInfo.getDestinationAirport());
        System.out.println("ACTUAL Arrival Airport: " + arrivalAirportText);
        Assert.assertTrue(
                arrivalAirportText.contains(storedFlightInfo.getDestinationAirport()),
                "Varış airport bilgisi eşleşmedi. Actual: " + arrivalAirportText
        );

        System.out.println("EXPECTED Transfer Type: Direkt");
        System.out.println("ACTUAL Transfer Type: " + storedFlightInfo.getTransferType());
        Assert.assertEquals(
                storedFlightInfo.getTransferType(),
                "Direkt",
                "Aktarma bilgisi eşleşmedi."
        );

        System.out.println("RESULT: Açılan uçuş detayları doğrulandı");
        System.out.println("==================================================");
    }

    @When("user opens first flight business package area")
    public void userOpensFirstFlightBusinessPackageArea() {
        System.out.println("STEP: İlk uçuşun Business alanı açılıyor");
        searchResultPage.openFirstFlightBusinessArea();
        System.out.println("RESULT: Business alanı açıldı");
        System.out.println("==================================================");
    }

    @Then("BusinessFly price should match selected business price")
    public void businessFlyPriceShouldMatchSelectedBusinessPrice() {
        System.out.println("STEP: BusinessFly paket fiyatı seçilen business fiyatı ile karşılaştırılıyor");

        String businessFlyPrice = searchResultPage.getBusinessFlyPrice();

        System.out.println("EXPECTED Business Price: " + storedFlightInfo.getBusinessPrice());
        System.out.println("ACTUAL BusinessFly Price: " + businessFlyPrice);

        Assert.assertEquals(
                businessFlyPrice,
                storedFlightInfo.getBusinessPrice(),
                "BusinessFly fiyatı seçilen business fiyatı ile eşleşmedi."
        );

        System.out.println("RESULT: BusinessFly fiyat doğrulaması başarılı");
        System.out.println("==================================================");
    }

    @When("user selects BusinessFly package")
    public void userSelectsBusinessFlyPackage() {
        System.out.println("STEP: BusinessFly paketi seçiliyor");
        searchResultPage.selectBusinessFlyPackage();
        System.out.println("RESULT: BusinessFly paket seçimi yapıldı");
        System.out.println("==================================================");
    }

    @Then("next page flight details should match stored values")
    public void nextPageFlightDetailsShouldMatchStoredValues() {

        System.out.println("STEP: Son ekrandaki uçuş bilgileri footer alanından doğrulanıyor");

        boolean isOpened = selectedFlightPage.isPageOpened();

        System.out.println("EXPECTED: Footer detay alanı görünmeli");
        System.out.println("ACTUAL: " + isOpened);

        Assert.assertTrue(
                isOpened,
                "Son ekran açılmadı."
        );

        String actualRouteInfo = selectedFlightPage.getRouteInfo();
        String actualDateInfo = selectedFlightPage.getDateInfo();
        String actualTimeInfo = selectedFlightPage.getTimeInfo();

        System.out.println("EXPECTED Route: " + storedFlightInfo.getOriginAirport() + " - " + storedFlightInfo.getDestinationAirport());
        System.out.println("ACTUAL Route: " + actualRouteInfo);
        Assert.assertTrue(
                actualRouteInfo.contains(storedFlightInfo.getOriginAirport())
                        && actualRouteInfo.contains(storedFlightInfo.getDestinationAirport()),
                "Route bilgisi eşleşmedi. Actual: " + actualRouteInfo
        );

        System.out.println("EXPECTED Date contains: 15 Nis");
        System.out.println("ACTUAL Date: " + actualDateInfo);
        Assert.assertTrue(
                actualDateInfo.contains("15 Nis"),
                "Tarih bilgisi eşleşmedi. Actual: " + actualDateInfo
        );

        System.out.println("EXPECTED Departure Time: " + storedFlightInfo.getDepartureTime());
        System.out.println("EXPECTED Arrival Time: " + storedFlightInfo.getArrivalTime());
        System.out.println("ACTUAL Time Info: " + actualTimeInfo);
        Assert.assertTrue(
                actualTimeInfo.contains(storedFlightInfo.getDepartureTime()),
                "Kalkış saati eşleşmedi. Actual: " + actualTimeInfo
        );
        Assert.assertTrue(
                actualTimeInfo.contains(storedFlightInfo.getArrivalTime()),
                "Varış saati eşleşmedi. Actual: " + actualTimeInfo
        );

        System.out.println("EXPECTED: toplam tutar alanı görünmeli");
        System.out.println("ACTUAL: " + selectedFlightPage.isTotalPriceDisplayed());
        Assert.assertTrue(
                selectedFlightPage.isTotalPriceDisplayed(),
                "Toplam tutar alanı görünmüyor."
        );

        System.out.println("EXPECTED: Devam butonu görünmeli");
        System.out.println("ACTUAL: " + selectedFlightPage.isContinueButtonDisplayed());
        Assert.assertTrue(
                selectedFlightPage.isContinueButtonDisplayed(),
                "Devam butonu görünmüyor."
        );

        System.out.println("RESULT: Son ekrandaki tarih, saat ve airport bilgileri doğrulandı");
        System.out.println("==================================================");
    }
}