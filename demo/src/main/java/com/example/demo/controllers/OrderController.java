package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.models.enums.OrderStatus;
import com.example.demo.models.enums.Specialization;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final com.example.demo.services.OrderService orderService;
    private final com.example.demo.services.MasterService masterService;
    private final UserService userService;

    @GetMapping("/orders")
    public String orders(@RequestParam(name = "title", required = false) String title, Principal principal, Model model){
        User user = orderService.getUserByPrincipal(principal);
        if (user.isAdmin() || user.isOwner()){
            model.addAttribute("orders", orderService.listAllOrders());
        }
        else{
            model.addAttribute("orders", orderService.listOrders(user));
        }
        model.addAttribute("masters", masterService.listAllMasters());
        model.addAttribute("specializations", Specialization.values());
        model.addAttribute("user", user);
        return "orders";
    }

    @PostMapping("/orders")
    public String createOrder(@RequestParam(name = "phone") String phone, com.example.demo.models.Order order, Principal principal) throws IOException {
        return "redirect:/orders/" + phone;
    }

    @GetMapping("/orders/{user_phone}")
    public String userOrders(@PathVariable String user_phone, Model model, Principal principal) throws IOException {
        User user = orderService.getUserByPrincipal(principal);
        User client = userService.findByPhoneNumber(user_phone);
        model.addAttribute("orders", orderService.listOrders(client));
        model.addAttribute("user", user);
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String orderInfo(@PathVariable Long id, Model model, Principal principal){
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("user", orderService.getUserByPrincipal(principal));
        model.addAttribute("statuses", OrderStatus.values());
        return "order_info";
    }

    @PostMapping("/order/create")
    public String createNewOrder(com.example.demo.models.Order order, Principal principal) throws IOException {
        orderService.saveOrder(principal, order);
        return "redirect:/orders";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) throws IOException {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
