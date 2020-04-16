package com.storyart.commentservice.dto.comment;


import com.storyart.commentservice.model.Comment;
import com.storyart.commentservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListCommentDTO {
    private int id;
    private int userId;
    private String username;
    private String userAvatarUrl;
    private int storyId;
    private String content;
    private boolean isActive;
    private boolean isDisableByAdmin;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Integer> likes;
    private List<Integer> dislikes;
//    à, lam v phuc tap lam
    //minh dung model mapper nha, ma thoi cu tam thoi v di
    //anh tem model mapper vao nha ok, de a xem thu
//   no map field giong nhau cua 2 object
    //truyen vao comment
}
