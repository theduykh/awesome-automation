package pages;

import org.openqa.selenium.Keys;
import org.theduykh.ata.test.AtaPage;

public class HomePage extends AtaPage {

    public HomePage viewTodayDeals() {
        try {
            I.click("//a[@aria-label=\"Today's Deals - See all deals\"] | //a[@aria-label=\"Deal of the Day - See all deals\"]");
        } catch (Exception e) {
            search("today deal");
            I.click("//span[text()=\"Today's deals - new deals every day\"]");
        }

        return this;
    }

    public HomePage backToMainPage() {
        I.click("#nav-logo-sprites");
        return this;
    }

    public HomePage search(String searching) {
        I.fillText("#twotabsearchtextbox", searching + Keys.ENTER);
        return this;
    }

    public HomePage openTheSecondDeal() {
        I.click("[aria-label='Deals grid'] > div > div:nth-of-type(2) a > div");
        return this;
    }

    public HomePage openAnItem() {
        I.click(".s-main-slot.s-result-list > div[data-asin][data-uuid]:nth-of-type(5) a > div");
        return this;
    }

    public HomePage sortBy(String sorting) {
        I.waitFor(3000);
        I.click(".a-dropdown-container > span > span > span");
        I.click("//div[@class='a-popover-wrapper']//li/a[text()='" + sorting + "']");
        return this;
    }

    public HomePage goToCart() {
        I.click("#nav-cart");
        return this;
    }

    protected double parsePrice(String price) {
        price = price.trim().replaceAll("\\$", "");
        return Double.parseDouble(price);
    }
}
