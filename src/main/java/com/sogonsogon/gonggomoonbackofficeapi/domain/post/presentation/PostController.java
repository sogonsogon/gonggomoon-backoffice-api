package com.sogonsogon.gonggomoonbackofficeapi.domain.post.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.application.PostService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostsResponse>> getPosts(
            @RequestParam(required = false) JobType type,
            @RequestParam(required = false) String name,
            Pageable pageable
            ) {

        Page<PostsResponse> posts = postService.getPosts(type, name, pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {

        return ResponseEntity.ok(postService.getPost(id));
    }
}
