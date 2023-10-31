package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final com.example.demo.repositories.OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<com.example.demo.models.Order> listMasters(User user) {
        if (user != null) return orderRepository.findByUser(user);
        return null;
    }

    public List<com.example.demo.models.Order> listAllMasters() {
        return orderRepository.findAll();
    }

    public void saveOrder(Principal principal, com.example.demo.models.Order order) throws IOException {
        order.setUser(getUserByPrincipal(principal));
        log.info("Saving new {}", order);
        orderRepository.save(order);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public com.example.demo.models.Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
