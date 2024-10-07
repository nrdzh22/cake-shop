package com.example.cakeshop.service;

import com.example.cakeshop.model.Cart;
import com.example.cakeshop.model.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    @Autowired
    private CartService cartService;

    // Process the checkout and optionally clear the cart
    public void processCheckout(Checkout checkout, Long cartId) {
        Cart cart = cartService.getCart(cartId);
    }
}
