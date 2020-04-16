package com.storyart.commentservice.dto.comment;

import com.storyart.commentservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentHistoryResponseDTO {
    private Integer id;
    private String storyName;
    private int storyId;
    private String content;
    private boolean isActive;
    private boolean isDisableByAdmin;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
