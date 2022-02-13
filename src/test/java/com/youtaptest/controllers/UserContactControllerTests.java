package com.youtaptest.controllers;

import com.youtaptest.client.UsersClient;
import com.youtaptest.models.User;
import com.youtaptest.models.UserContact;
import com.youtaptest.services.UserContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserContactController.class)
@Import(UserContactService.class)
public class UserContactControllerTests {

    @MockBean
    UsersClient usersClient;

    @Autowired
    private WebTestClient webTestClient;

    User user1 = User.builder()
            .id(1L)
            .username("Bret")
            .email("Sincere@april.biz")
            .phone("1-770-736-8031 x56442")
            .build();

    User user2 = User.builder()
            .id(2L)
            .username("Antonette")
            .email("Shanna@melissa.tv")
            .phone("010-692-6593 x09125")
            .build();

    UserContact userContact1 = UserContact.builder()
            .id(1L)
            .email("Sincere@april.biz")
            .phone("1-770-736-8031 x56442")
            .build();

    UserContact userContact2 = UserContact.builder()
            .id(2L)
            .email("Shanna@melissa.tv")
            .phone("010-692-6593 x09125")
            .build();

    UserContact emptyUserContact = UserContact.builder()
            .id(-1L)
            .build();

    @Test
    void testGetUserContactsById() {
        List<User> userList = List.of(user1);
        Mockito.when(usersClient.getUsersBy(Optional.of(List.of(1L)), Optional.empty()))
                .thenReturn(Mono.just(userList));

        webTestClient.get().uri("/getusercontacts?id=1")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(userContact1);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.of(List.of(1L)), Optional.empty());
    }

    @Test
    void testGetUserContactsByMultipleIds() {
        List<User> userList = List.of(user1, user2);
        Mockito.when(usersClient.getUsersBy(Optional.of(List.of(1L, 2L)), Optional.empty()))
                .thenReturn(Mono.just(userList));

        webTestClient.get().uri("/getusercontacts?id=1&id=2")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(userContact1, userContact2);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.of(List.of(1L, 2L)), Optional.empty());
    }

    @Test
    void testGetUserContactsByIdNotFound() {
        Mockito.when(usersClient.getUsersBy(Optional.of(List.of(10000L)), Optional.empty()))
                .thenReturn(Mono.just(Collections.emptyList()));

        webTestClient.get().uri("/getusercontacts?id=10000")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(emptyUserContact);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.of(List.of(10000L)), Optional.empty());
    }

    @Test
    void testGetUserContactsByUsername() {
        List<User> userList = List.of(user1);
        Mockito.when(usersClient.getUsersBy(Optional.empty(), Optional.of(List.of("Bret"))))
                .thenReturn(Mono.just(userList));

        webTestClient.get().uri("/getusercontacts?username=Bret")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(userContact1);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.empty(), Optional.of(List.of("Bret")));
    }

    @Test
    void testGetUserContactsByMultipleUsernames() {
        List<User> userList = List.of(user1, user2);
        Mockito.when(usersClient.getUsersBy(Optional.empty(), Optional.of(List.of("Bret", "Antonette"))))
                .thenReturn(Mono.just(userList));

        webTestClient.get().uri("/getusercontacts?username=Bret&username=Antonette")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(userContact1, userContact2);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.empty(), Optional.of(List.of("Bret", "Antonette")));
    }

    @Test
    void testGetUserContactsByUsernameNotFound() {
        Mockito.when(usersClient.getUsersBy(Optional.empty(), Optional.of(List.of("SomeUsernameThatDoesntExists"))))
                .thenReturn(Mono.just(Collections.emptyList()));

        webTestClient.get().uri("/getusercontacts?username=SomeUsernameThatDoesntExists")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(emptyUserContact);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.empty(), Optional.of(List.of("SomeUsernameThatDoesntExists")));
    }

    @Test
    void testGetUserContactsByIdAndUsername() {
        List<User> userList = List.of(user1);
        Mockito.when(usersClient.getUsersBy(Optional.of(List.of(1L)), Optional.of(List.of("Bret"))))
                .thenReturn(Mono.just(userList));

        webTestClient.get().uri("/getusercontacts?id=1&username=Bret")
                .header(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserContact.class)
                .contains(userContact1);

        Mockito.verify(usersClient, times(1))
                .getUsersBy(Optional.of(List.of(1L)), Optional.of(List.of("Bret")));
    }
}
