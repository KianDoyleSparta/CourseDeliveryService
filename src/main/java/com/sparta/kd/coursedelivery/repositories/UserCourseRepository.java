package com.sparta.kd.coursedelivery.repositories;

import com.sparta.kd.coursedelivery.entities.UserCourse;
import com.sparta.kd.coursedelivery.entities.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
}