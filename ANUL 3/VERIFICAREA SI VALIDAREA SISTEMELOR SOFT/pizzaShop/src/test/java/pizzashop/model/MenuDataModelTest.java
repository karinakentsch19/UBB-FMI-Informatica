package pizzashop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuDataModelTest {
    private String mItem = "12";
    private Integer mQuantity = 12;
    private Double mPrice = 12.2;

    private String mItemUpdated = "123";
    private Integer mQuantityUpdated = 123;
    private Double mPriceUpdated = 13.2;
    @Test
    void createAndReadTest(){
        MenuDataModel menuDataModel = new MenuDataModel(mItem, mQuantity, mPrice);
        assertEquals(menuDataModel.getMenuItem(), mItem);
        assertEquals(menuDataModel.getQuantity(), mQuantity);
        assertEquals(menuDataModel.getPrice(), mPrice);
    }

    @Test
    void setTest(){
        MenuDataModel menuDataModel = new MenuDataModel(mItem, mQuantity, mPrice);
        menuDataModel.setMenuItem(mItemUpdated);
        menuDataModel.setQuantity(mQuantityUpdated);
        menuDataModel.setPrice(mPriceUpdated);
        assertEquals(menuDataModel.getMenuItem(), mItemUpdated);
        assertEquals(menuDataModel.getQuantity(), mQuantityUpdated);
        assertEquals(menuDataModel.getPrice(), mPriceUpdated);
    }
}