package com.example.cakeshop.service;

import com.example.cakeshop.model.CustomerOrder;
import com.example.cakeshop.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public CustomerOrder saveOrder(CustomerOrder order) {
        return customerOrderRepository.save(order);
    }

    public CustomerOrder findOrderById(Long id) {
        return customerOrderRepository.findById(id).orElse(null);
    }

}
