package entities;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CartEntity {
    @Getter
    private final List<ItemEntity> itemsList = new ArrayList<>();

    public void addItem(ProductEntity product, int quantity) {
        itemsList.add(new ItemEntity(product, quantity));
    }
}
