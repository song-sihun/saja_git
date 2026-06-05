package org.example.friendapp.service;

import lombok.RequiredArgsConstructor;
import org.example.friendapp.domain.Friend;
import org.example.friendapp.repository.FriendRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional(readOnly = true)
    public Page<Friend> getFriends(Pageable pageable) {
        return friendRepository.findAll(pageable);
    }

    @Transactional
    public void deleteFriend(Long id) {
        friendRepository.deleteById(id);
    }

    @Transactional
    public Friend saveFriend(Friend friend) {
        return friendRepository.save(friend);
    }

    @Transactional(readOnly = true)
    public Friend getFriendById(Long id) {
        return friendRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found. id=" + id));
    }

    @Transactional
    public void updateFriend(Long id, Friend updatedFriend) {
        Friend friend = getFriendById(id);
        friend.setName(updatedFriend.getName());
        friend.setEmail(updatedFriend.getEmail());
        friendRepository.save(friend);
    }


}
