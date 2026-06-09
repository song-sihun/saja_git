package org.lion.springdatajps;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findUsersByNameLike(String name){
        return userRepository.findUsersByNameLike(name);
    }

    @Transactional
    public User update(Long id, User updateUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found user"));
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());

        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public List<User> findAllUsersByNameLike(String name) {
        return userRepository.findUsersByNameLike(name);
    }

    public List<User> findAllUsersByNameContaining(String name) {
        return userRepository.findUsersByNameContaining(name);
    }

//
//    @Override
//    public List<User> findUsersByName(String name) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
//        Root<User> user = query.from(User.class);
//        query.select(user).where(criteriaBuilder.like(user.get("name"),"%"+name+"%"));
//        //select user from User u where u.name like %:name%
//
//        return entityManager.createQuery(query).getResultList();
//    }




}
