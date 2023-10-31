package com.example.demo.controllers;

import com.example.demo.models.User;
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
public class MasterController {
    private final com.example.demo.services.MasterService masterService;

    @GetMapping(value={"/","/masters"})
    public String masters(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {
        User user = masterService.getUserByPrincipal(principal);
        model.addAttribute("masters", masterService.listMasters(title));
        model.addAttribute("user", user);
        return "masters";
    }

    @GetMapping("/master/{id}")
    public String masterInfo(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("user", masterService.getUserByPrincipal(principal));
        model.addAttribute("master", masterService.getMasterById(id));
        return "master_info";
    }

    @PostMapping("/master/create")
    public String masterCreate(com.example.demo.models.Master master) throws IOException {
        masterService.saveMaster(master);
        return "redirect:/masters";
    }

    @PostMapping("/master/delete/{id}")
    public String masterDelete(@PathVariable Long id) throws IOException {
        masterService.deleteMaster(id);
        return "redirect:/masters";
    }
}
