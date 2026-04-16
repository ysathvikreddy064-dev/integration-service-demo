package com.sathvik.integration.controller;

import com.sathvik.integration.model.ExternalUser;
import com.sathvik.integration.service.ExternalUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external-users")
public class ExternalUserController {

    private final ExternalUserService service;

    public ExternalUserController(ExternalUserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ExternalUser getUser(@PathVariable Long id) {
        return service.getUser(id);
    }
}