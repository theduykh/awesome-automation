package pages;

import org.theduykh.ata.test.AtaPage;

public class ProductPage extends AtaPage {

    public ProductPage setQuantity(int quantity) {
        I.selectByValue("#quantity", String.valueOf(quantity));
        return this;
    }

    public ProductPage addToCart() {
        I.click("#add-to-cart-button");
        return this;
    }

    public String getProductName() {
        return I.getText("#productTitle");
    }

    public double getCurrentPrice() {
        String priceDisplay = I.getText("#price_inside_buybox");
        priceDisplay = priceDisplay.replaceAll("\\$", "");
        return Double.parseDouble(priceDisplay);
    }
}
