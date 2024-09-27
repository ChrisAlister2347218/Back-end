package com.blog.blog_backend.repository;

import com.blog.blog_backend.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    // Method to find posts by author (username)
    List<Post> findByUsername(String username);
}
