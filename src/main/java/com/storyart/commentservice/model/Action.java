package com.storyart.commentservice.model;

import com.storyart.commentservice.common.DateAudit;
import com.storyart.commentservice.common.constants.ACTION_TYPES;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "action")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Action extends DateAudit {
    @Id
    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String screenId;

    @Size(min = 10, max = 10000)
    @Column(length = 10000)
    private String content;

    @Size(max = 255)
    private String nextScreenId;
//    private String operation;//+ - * /

    @NotBlank
    @Size(max = 255)
    private String value; // gia tri tac dong

    @Enumerated(EnumType.STRING)
    private ACTION_TYPES type;

}
