package com.example.cakeshop.controller;

import com.example.cakeshop.model.Cart;
import com.example.cakeshop.model.Checkout;
import com.example.cakeshop.model.CustomerOrder;
import com.example.cakeshop.service.CartService;
import com.example.cakeshop.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Controller
@SessionAttributes("cartId")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/checkout")
    public String showCheckoutPage(@ModelAttribute("cartId") Long cartId, Model model) {
        Cart cart = cartService.getCart(cartId);
        model.addAttribute("cart", cart);
        BigDecimal total = cart.getTotal();
        DecimalFormat df = new DecimalFormat("RM0.00");
        String formattedTotal = df.format(total.doubleValue());
        model.addAttribute("total", formattedTotal);
        model.addAttribute("checkout", new Checkout());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute("checkout") Checkout checkout,
                                  @ModelAttribute("cartId") Long cartId,
                                  SessionStatus sessionStatus,
                                  Model model) {
        // Validate checkout data
        if (checkout.getFullName() == null || checkout.getFullName().trim().isEmpty() ||
                checkout.getNotes() == null || checkout.getNotes().trim().isEmpty() ||
                checkout.getAddress() == null || checkout.getAddress().trim().isEmpty() ||
                checkout.getPhoneNumber() == null || checkout.getPhoneNumber().trim().isEmpty()) {
            model.addAttribute("error", "All fields are required.");
            return "checkout";
        }
        Cart cart = cartService.getCart(cartId);
        // Create a new CustomerOrder
        CustomerOrder order = new CustomerOrder();
        order.setFullName(checkout.getFullName());
        order.setNotes(checkout.getNotes());
        order.setAddress(checkout.getAddress());
        order.setPhoneNumber(checkout.getPhoneNumber());
        order.setTotalAmount(cart.getTotal());
        order.setCartId(cartId); // Set the cartId
        // Save the order
        CustomerOrder savedOrder = customerOrderService.saveOrder(order);

        sessionStatus.setComplete();

        // Redirect to the receipt page with order ID
        return "redirect:/receipt/" + savedOrder.getId();
    }

    @GetMapping("/receipt/{orderId}")
    public String showReceiptPage(@PathVariable("orderId") Long orderId, Model model) {
        CustomerOrder order = customerOrderService.findOrderById(orderId);
        model.addAttribute("order", order);
        return "receipt";
    }
}
