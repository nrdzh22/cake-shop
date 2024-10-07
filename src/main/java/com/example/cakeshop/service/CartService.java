package com.example.cakeshop.service;

import com.example.cakeshop.model.Cart;
import com.example.cakeshop.model.Product;
import com.example.cakeshop.repository.CartRepository;
import com.example.cakeshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElse(new Cart());
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cart.addItem(product, quantity);
            saveCart(cart);
        }
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cart.removeItem(product);
            saveCart(cart);
        }
    }
}
