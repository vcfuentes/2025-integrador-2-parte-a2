package es.upm.grise.profundizacion.order;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class OrderTest {

    private Order order;
    private Item itemMock;
    private Product productMock;

    @BeforeEach
    void setUp() {
        order = new Order();
        itemMock = mock(Item.class);
        productMock = mock(Product.class);
        when(itemMock.getProduct()).thenReturn(productMock);
        when(productMock.getId()).thenReturn(1L);
    }

    @Test
    void addItem_ShouldAddNewItem_WhenValidItem() throws IncorrectItemException {
        when(itemMock.getPrice()).thenReturn(10.0);
        when(itemMock.getQuantity()).thenReturn(1);

        order.addItem(itemMock);

        Collection<Item> items = order.getItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(itemMock));
    }

    @Test
    void addItem_ShouldThrowException_WhenPriceIsNegative() {
        when(itemMock.getPrice()).thenReturn(-5.0);
        when(itemMock.getQuantity()).thenReturn(1);

        assertThrows(IncorrectItemException.class, () -> order.addItem(itemMock));
    }

    @Test
    void addItem_ShouldThrowException_WhenQuantityIsZero() {
        when(itemMock.getPrice()).thenReturn(10.0);
        when(itemMock.getQuantity()).thenReturn(0);

        assertThrows(IncorrectItemException.class, () -> order.addItem(itemMock));
    }

    @Test
    void addItem_ShouldUpdateQuantity_WhenSameProductAndSamePrice() throws IncorrectItemException {
        Item existingItem = mock(Item.class);
        Product existingProduct = mock(Product.class);
        when(existingItem.getProduct()).thenReturn(existingProduct);
        when(existingProduct.getId()).thenReturn(1L);
        when(existingItem.getPrice()).thenReturn(10.0);
        when(existingItem.getQuantity()).thenReturn(2);

        order.getItems().add(existingItem);

        when(itemMock.getPrice()).thenReturn(10.0);
        when(itemMock.getQuantity()).thenReturn(1);

        order.addItem(itemMock);

        verify(existingItem).setQuantity(3);
        assertEquals(1, order.getItems().size());
    }

    @Test
    void addItem_ShouldAddNewItem_WhenSameProductButDifferentPrice() throws IncorrectItemException {
        Item existingItem = mock(Item.class);
        Product existingProduct = mock(Product.class);
        when(existingItem.getProduct()).thenReturn(existingProduct);
        when(existingProduct.getId()).thenReturn(1L);
        when(existingItem.getPrice()).thenReturn(15.0);
        when(existingItem.getQuantity()).thenReturn(2);

        order.getItems().add(existingItem);

        when(itemMock.getPrice()).thenReturn(10.0);
        when(itemMock.getQuantity()).thenReturn(1);

        order.addItem(itemMock);

        assertEquals(2, order.getItems().size());
        assertTrue(order.getItems().contains(itemMock));
    }

    @Test
    void addItem_ShouldAddNewItem_WhenDifferentProduct() throws IncorrectItemException {
        Item existingItem = mock(Item.class);
        Product existingProduct = mock(Product.class);
        when(existingItem.getProduct()).thenReturn(existingProduct);
        when(existingProduct.getId()).thenReturn(2L);
        when(existingItem.getPrice()).thenReturn(10.0);
        when(existingItem.getQuantity()).thenReturn(1);

        order.getItems().add(existingItem);

        when(itemMock.getPrice()).thenReturn(10.0);
        when(itemMock.getQuantity()).thenReturn(1);

        order.addItem(itemMock);

        assertEquals(2, order.getItems().size());
        assertTrue(order.getItems().contains(itemMock));
    }
}
