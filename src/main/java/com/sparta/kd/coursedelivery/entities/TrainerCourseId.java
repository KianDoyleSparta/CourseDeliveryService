package com.sparta.kd.coursedelivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrainerCourseId implements Serializable {
    private static final long serialVersionUID = -5333277978572922453L;
    @Column(name = "trainer_id", nullable = false)
    private Integer trainerId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
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
        TrainerCourseId entity = (TrainerCourseId) o;
        return Objects.equals(this.courseId, entity.courseId) &&
                Objects.equals(this.trainerId, entity.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, trainerId);
    }

}