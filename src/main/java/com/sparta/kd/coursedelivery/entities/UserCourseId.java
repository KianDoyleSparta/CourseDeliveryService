package com.sparta.kd.coursedelivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCourseId implements Serializable {
    private static final long serialVersionUID = 2767002444952478967L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserCourseId entity = (UserCourseId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.courseId, entity.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }

}