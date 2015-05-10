// This is the session bean (POJO) for the user-oriented web pages
// It is created in the UserWelcomeController, and if the user happens
// on another page first, the user is forwarded to that page.
// This bean holds information for the user across the various
// page visits.
package cs636.music.presentation.web;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.User;

public class UserBean {

	private User user; 
	private Product product;
	private Cart cart;

	public UserBean() {}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
}
