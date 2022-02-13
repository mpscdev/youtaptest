package com.youtaptest.services;

import com.youtaptest.client.UsersClient;
import com.youtaptest.models.User;
import com.youtaptest.models.UserContact;
import com.youtaptest.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserContactService {

    private static final Logger logger = LoggerFactory.getLogger(UserContactService.class);
    private final UsersClient client;

    @Autowired
    public UserContactService(UsersClient client) {
        this.client = client;
    }

    static Function<User, UserContact> toUserContact = user ->
            UserContact.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build();

    private static void responseHandler(
            List<User> users,
            SynchronousSink<List<UserContact>> synchronousSink) {
        if (users.isEmpty()) {
            synchronousSink.next(List.of(UserContact.builder().id(-1L).build()));
        } else {
            synchronousSink.next(Utils.transform(users, toUserContact));
        }
    }

    public Mono<List<UserContact>> getUserContactsBy(
            Optional<List<Long>> id,
            Optional<List<String>> username) {
        logger.debug("Get user contacts by id:{} username:{}", id, username);
        return this.client.getUsersBy(id, username)
                .onErrorReturn(Collections.emptyList())
                .handle((UserContactService::responseHandler));
    }
}
