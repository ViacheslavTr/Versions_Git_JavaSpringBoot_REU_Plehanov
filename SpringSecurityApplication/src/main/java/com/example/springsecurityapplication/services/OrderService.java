package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
    public Order getOrderId(int id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }
    @Transactional
    public void updateOrder(int id, Order order){
        order.setId(id);
        orderRepository.save(order);
    }
}
