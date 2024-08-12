package com.sparta.kd.coursedelivery.repositories;

import com.sparta.kd.coursedelivery.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}