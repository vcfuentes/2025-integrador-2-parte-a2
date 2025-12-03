package es.upm.grise.profundizacion.order;

import java.util.ArrayList;
import java.util.Collection;

public class Order {

    private Collection<Item> items;

	/*
	 * Constructor
	 */
    public Order() {
        items = new ArrayList<>();
    }

	/*
	 * Method to code / test
	 */
	public void addItem(Item item) throws IncorrectItemException {
		validateItem(item);

		if (!tryUpdateExistingItem(item)) {
			addNewItem(item);
		}
	}

	private void validateItem(Item item) throws IncorrectItemException {
		if (item.getPrice() < 0 || item.getQuantity() <= 0) {
			throw new IncorrectItemException();
		}
	}

	private boolean tryUpdateExistingItem(Item item) {
		for (Item existingItem : items) {
			if (isSameProduct(existingItem, item)) {
				updateQuantityOrAdd(existingItem, item);
				return true;
			}
		}
		return false;
	}

	private boolean isSameProduct(Item existingItem, Item newItem) {
		return existingItem.getProduct().getId() == newItem.getProduct().getId();
	}

	private void updateQuantityOrAdd(Item existingItem, Item newItem) {
		if (existingItem.getPrice() == newItem.getPrice()) {
			existingItem.setQuantity(existingItem.getQuantity() + 1);
		} else {
			items.add(newItem);
		}
	}

	private void addNewItem(Item item) {
		items.add(item);
	}

    
	/*
	 * Setters/getters
	 */
    public Collection<Item> getItems() {
    	
    	return this.items;
    	
    }

}

