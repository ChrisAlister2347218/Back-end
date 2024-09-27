package com.blog.blog_backend.controller;

import com.blog.blog_backend.model.Post;
import com.blog.blog_backend.repository.PostRepository;
import com.blog.blog_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        String username = getCurrentUsername();
        post.setUsername(username);  // Set the username directly
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());

        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    // Retrieve a post by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable String id) {
        String username = getCurrentUsername();
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            if (post.get().getUsername().equals(username)) { // Check username instead of author
                return ResponseEntity.ok(post.get());
            } else {
                return ResponseEntity.status(403).body("You do not have permission to view this post.");
            }
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }

    // Update a post
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id, @RequestBody Post postDetails) {
        String username = getCurrentUsername();
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getUsername().equals(username)) { // Check username instead of author
                post.setTitle(postDetails.getTitle());
                post.setContent(postDetails.getContent());
                post.setUpdatedAt(new Date());
                return ResponseEntity.ok(postRepository.save(post));
            } else {
                return ResponseEntity.status(403).body("You do not have permission to edit this post.");
            }
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        String username = getCurrentUsername();
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getUsername().equals(username)) { // Check username instead of author
                postRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(403).body("You do not have permission to delete this post.");
            }
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    // Retrieve posts by username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username) {
        List<Post> posts = postRepository.findByUsername(username); // Update to find by username
        return ResponseEntity.ok(posts);
    }

    // Get the current username from the security context
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }
}