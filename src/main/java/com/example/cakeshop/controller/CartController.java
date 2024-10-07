package com.example.cakeshop.controller;

import com.example.cakeshop.model.Cart;
import com.example.cakeshop.model.Product;
import com.example.cakeshop.service.CartService;
import com.example.cakeshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("cartId")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("cartId")
    public Long getCartId(HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            cartId = cartService.createCart().getId();
            session.setAttribute("cartId", cartId);
        }
        return cartId;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            cartId = getCartId(session);
        }
        cartService.addProductToCart(cartId, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            cartId = getCartId(session);
        }
        Cart cart = cartService.getCart(cartId);
        Map<Long, Product> productCatalog = productService.getAllProducts().stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        model.addAttribute("cart", cart);
        model.addAttribute("products", productCatalog);

        // Format total price with two decimal places
        BigDecimal total = cart.getTotal();
        DecimalFormat df = new DecimalFormat("RM0.00");
        String formattedTotal = df.format(total.doubleValue());

        model.addAttribute("total", formattedTotal);
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            cartId = getCartId(session);
        }
        cartService.removeProductFromCart(cartId, productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            cartId = getCartId(session);
        }
        // Redirect to the checkout page handled by CheckoutController
        return "redirect:/checkout";
    }
}
