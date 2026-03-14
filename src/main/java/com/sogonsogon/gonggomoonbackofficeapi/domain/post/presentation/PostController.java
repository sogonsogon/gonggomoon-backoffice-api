package com.sogonsogon.gonggomoonbackofficeapi.domain.post.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.application.PostService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.CreatePostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(@RequestBody CreatePostRequest request) {

        postService.createPost(request);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<BaseResponse.PageResponse<PostListResponse>>> getPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<PostStatus> statuses,
            Pageable pageable
            ) {

        Page<PostListResponse> response = postService.getPosts(title, statuses, pageable);

        return ResponseEntity.ok(BaseResponse.success(
                BaseResponse.PageResponse.<PostListResponse>builder()
                        .content(response.getContent())
                        .pageInfo(BaseResponse.PageInfo.builder()
                                .currentPage(response.getNumber())
                                .totalPages(response.getTotalPages())
                                .totalElements(response.getTotalElements())
                                .hasNext(response.hasNext())
                                .build())
                        .build())
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(@PathVariable Long postId) {

        return ResponseEntity.ok(BaseResponse.success(postService.getPost(postId)));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> publish(@PathVariable Long postId) {

        postService.publishPost(postId);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long postId) {

        postService.deletePost(postId);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
