package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
