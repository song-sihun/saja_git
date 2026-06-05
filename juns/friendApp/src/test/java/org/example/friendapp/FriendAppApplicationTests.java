package org.example.friendapp;

import org.example.friendapp.repository.FriendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FriendAppApplicationTests {

    @Autowired
    private FriendRepository friendRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void pagingAndSorting() {
        friendRepository.findAll(PageRequest.of(0, 2)).forEach(System.out::println);
    }

}
