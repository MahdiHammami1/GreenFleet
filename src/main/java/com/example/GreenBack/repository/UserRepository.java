package com.example.GreenBack.repository;

import com.example.GreenBack.entity.User;
import com.example.GreenBack.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
    List<User> findAllByStatus(Status status);

    @Query("SELECT u FROM User u WHERE u.userId IN :ids")
    List<User> findAllByIdIn(List<Long> ids);

}
