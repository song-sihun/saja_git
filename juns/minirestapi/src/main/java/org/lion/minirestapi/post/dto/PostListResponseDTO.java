package org.lion.minirestapi.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.lion.minirestapi.post.domain.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostListResponseDTO {
    private Long id;
    private String title;
    private String category;
    private String imageUrl;
    private String userName;
    private LocalDateTime createdAt;

    public static PostListResponseDTO fromEntity(Post post) {
        return PostListResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .imageUrl(post.getImageUrl())
                .userName(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
