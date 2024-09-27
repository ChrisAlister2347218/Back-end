package com.blog.blog_backend.repository;

import com.blog.blog_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email); // Existing method to find user by email
    Optional<User> findByUsername(String username); // New method to find user by username
}
