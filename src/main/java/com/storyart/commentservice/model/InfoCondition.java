package com.storyart.commentservice.model;

import com.storyart.commentservice.common.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "info_condition")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoCondition extends DateAudit {
    @Id
    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String informationId;

    @Size(max = 255)
    @NotBlank
    private String type; // >, <, =, >=, <=

    @Size(max = 255)
    @NotBlank
    private String value;

    @Size(max = 255)
    @NotBlank
    private String nextScreenId;
}
