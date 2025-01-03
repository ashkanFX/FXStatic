package com.example.FXstatic.controllers;

import com.example.FXstatic.dto.PostReqDto;
import com.example.FXstatic.models.Post;
import com.example.FXstatic.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping()
    public Post creatPost(@RequestBody PostReqDto postReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.creatPost(userDetails, postReqDto);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostReqDto postReqDto) {
        return postService.updatePost(id, postReqDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

//    @GetMapping()
//    public List<Post> getPostOfUser(@AuthenticationPrincipal UserDetails userDetails) {
//        String name = userDetails.getUsername();
//        return postService.getPostOfUser(name);
//    }

    @GetMapping("get/all")
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("public/get/latest")
    public List<Post> getLatestPost() {
        return postService.getLatestPost();
    }
    @GetMapping("public/get/{id}")
    public Post getLatestPost(@PathVariable Long id) {
        return postService.findAllById(id);
    }

//    @GetMapping("/get/{id}")
//    public List<Post> getPostByUserId(@PathVariable String id) {
//        return postService.getPostOfUser(id);
//    }
}
