
package business.shoppingcartsubsystem;

import java.util.List;

import business.externalinterfaces.IShoppingCart;


public class ShoppingCart implements IShoppingCart {

    @SuppressWarnings({ "unused", "unchecked" })
	private List cartItems;
    
    @SuppressWarnings("unchecked")
	ShoppingCart(List cartItems) {
        this.cartItems = cartItems;
    }
    
}
