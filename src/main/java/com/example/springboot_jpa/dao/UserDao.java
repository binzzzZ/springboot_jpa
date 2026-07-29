package com.example.springboot_jpa.dao;

import com.example.springboot_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
