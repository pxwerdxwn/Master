package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterService {
    private final com.example.demo.repositories.MasterRepository masterRepository;
    private final UserRepository userRepository;

    public List<com.example.demo.models.Master> listMasters(String title) {
        if (title != null) return masterRepository.findByTitle(title);
        return masterRepository.findAll();
    }

    public List<com.example.demo.models.Master> listAllMasters() {
        return masterRepository.findAll();
    }

    public void saveMaster(com.example.demo.models.Master master) {
        log.info("Saving new {}", master);
        masterRepository.save(master);
    }

    public void deleteMaster(Long id) {
        masterRepository.deleteById(id);
    }

    public com.example.demo.models.Master getMasterById(Long id) {
        return masterRepository.findById(id).orElse(null);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}
