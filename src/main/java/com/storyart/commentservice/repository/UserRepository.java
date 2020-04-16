package com.storyart.commentservice.repository;

import com.storyart.commentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("select u.id from User u where u.username like concat('%',?1,'%') or u.email like concat('%',?1,'%')")
    List<Integer> findUserIdsBySearchString(String searchString);
}
