package pages;

import entities.ItemEntity;
import entities.ProductEntity;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends HomePage {

    public CartPage editQuantity(int itemOrder, int quantity) {
        String locator = "//div[@data-name='Active Items']/div[@data-asin][" + itemOrder + "]//span[@class='sc-action-quantity']//select";
        I.selectByValue(locator, String.valueOf(quantity));
        I.waitFor(5000);
        return this;
    }

    public CartPage deleteItem(int itemOrder) {
        I.click("(//span[@data-action='delete'])[" + itemOrder + "]/span");
        I.waitFor(5000);
        return this;
    }

    public CartPage proceedCheckout() {
        I.click("#sc-buy-box-ptc-button");
        return this;
    }

    public double getTotalPrice() {
        return parsePrice(I.getText("#sc-subtotal-amount-activecart"));
    }

    public int getTotalQuantity() {
        String total = I.getText("#sc-subtotal-label-activecart").trim();
        return Integer.parseInt(total.split("\\(")[1].split(" ")[0]);
    }

    public List<ItemEntity> getItems() {
        List<ItemEntity> itemList = new ArrayList<>();
        int itemsCount = I.findElements("//div[@data-name='Active Items']/div[@data-asin][not(@data-removed)]").size();
        for (int i = itemsCount; i >= 1; i--) {
            ProductEntity productEntity = new ProductEntity();
            String priceLocator = "//div[@data-name='Active Items']/div[@data-asin][not(@data-removed)][" + i + "]//span[contains(@class,'sc-product-price')]";
            double price = parsePrice(I.getText(priceLocator));

            productEntity.setPrice(price);

            String quantityLocator = "//div[@data-name='Active Items']/div[@data-asin][" + i + "]//select[@name='quantity']";
            Select select = new Select(I.findElement(quantityLocator));
            WebElement option = select.getFirstSelectedOption();
            int quantity = Integer.parseInt(option.getText());

            itemList.add(new ItemEntity(productEntity, quantity));
        }

        return itemList;
    }
}
