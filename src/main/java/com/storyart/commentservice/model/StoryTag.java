package com.storyart.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class StoryTagId implements Serializable {
    private int tagId;
    private int storyId;
}

@Entity
@Table(name = "story_tag")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StoryTagId.class)
public class StoryTag {
    @Id
    private int tagId;

    @Id
    private int storyId;
}
