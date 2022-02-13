package com.youtaptest.controllers;

import com.youtaptest.models.UserContact;
import com.youtaptest.services.UserContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
public class UserContactController {

    private static final Logger logger = LoggerFactory.getLogger(UserContactController.class);
    private final UserContactService userContactService;

    @Autowired
    public UserContactController(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @GetMapping("/getusercontacts")
    public Mono<List<UserContact>> getUserContacts(
            @RequestParam Optional<List<Long>> id,
            @RequestParam Optional<List<String>> username) {
        logger.info("Get user contacts by id:{} username:{}", id, username);
        return this.userContactService.getUserContactsBy(id, username);
    }
}
