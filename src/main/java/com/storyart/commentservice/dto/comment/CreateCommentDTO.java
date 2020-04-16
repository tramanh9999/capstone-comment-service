package com.storyart.commentservice.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    private String content;
    private int userId;
    private int storyId;
    //cai nay bi bat loi nhieu lam,,  chphai de private và chi dc truy cap bang getter setter, chac a quen, hoac nham` :v ok
}
