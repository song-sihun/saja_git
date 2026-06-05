package org.example.friendapp.repository;

import org.example.friendapp.domain.Friend;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
class FriendRepositoryTest {
    @Autowired
    private FriendRepository friendRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {

    }

    @Test
    void findById() {

    }

    @Test
    void save() {
        Friend friend = new Friend("Test", "Test@email.com");
        Friend savedFriend = friendRepository.save(friend);
        assertThat(savedFriend).isEqualTo(friend);
    }

    @Test
    void deleteById() {

    }



}