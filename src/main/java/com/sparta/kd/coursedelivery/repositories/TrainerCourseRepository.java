package com.sparta.kd.coursedelivery.repositories;

import com.sparta.kd.coursedelivery.entities.TrainerCourse;
import com.sparta.kd.coursedelivery.entities.TrainerCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerCourseRepository extends JpaRepository<TrainerCourse, TrainerCourseId> {
}