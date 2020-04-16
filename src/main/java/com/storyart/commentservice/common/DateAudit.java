package com.storyart.commentservice.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
/* to allow an entity to inherit properties from a base class.
Unlike the @Inheritance annotation which maps the Java Object
inheritance to a relational database model which emulates inheritance,
@MappedSuperclass only models inheritance in the OOP world.
From a database perspective, the @MappedSuperclass inheritance model
is invisible since all the base class properties are simply
 copied to the database table mapped by the actual entity class
 https://vladmihalcea.com/how-to-inherit-properties-from-a-base-class-entity-using-mappedsuperclass-with-jpa-and-hibernate/*/

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class DateAudit implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
