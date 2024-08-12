package com.sparta.kd.coursedelivery.services;

import com.sparta.kd.coursedelivery.entities.*;
import com.sparta.kd.coursedelivery.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class CourseDeliveryService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final TrainerCourseRepository trainerCourseRepository;


    public CourseDeliveryService(UserRepository userRepository,
                                 TrainerRepository trainerRepository,
                                 CourseRepository courseRepository,
                                 UserCourseRepository userCourseRepository,
                                 TrainerCourseRepository trainerCourseRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.courseRepository = courseRepository;
        this.userCourseRepository = userCourseRepository;
        this.trainerCourseRepository = trainerCourseRepository;
    }

    // Create
    public void createUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public void createTrainer(Trainer trainer) {
        trainerRepository.saveAndFlush(trainer);
    }

    public void createCourse(Course course) {
        courseRepository.saveAndFlush(course);
    }

    public void createUserCourse(UserCourse userCourse) {
        userCourseRepository.saveAndFlush(userCourse);
    }

    public void createTrainerCourse(TrainerCourse trainerCourse) {
        trainerCourseRepository.saveAndFlush(trainerCourse);
    }

    // Read
    public void getUser(Integer id) {
        userRepository.findById(id);
    }

    public void getTrainer(Integer id) {
        trainerRepository.findById(id);
    }

    public void getCourse(Integer id) {
        courseRepository.findById(id);
    }

    public void getUserCourse(Integer userId, Integer courseId) {
        UserCourseId userCourseID = new UserCourseId();
        userCourseID.setUserId(userId);
        userCourseID.setCourseId(courseId);
        userCourseRepository.findById(userCourseID);
    }

    public void getTrainerCourse(Integer id) {
        TrainerCourseId trainerCourseId = new TrainerCourseId();
        trainerCourseId.setTrainerId(id);
        trainerCourseId.setCourseId(id);
        trainerCourseRepository.findById(trainerCourseId);
    }

    // Update

    // Delete
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteTrainer(Integer id) {
        trainerRepository.deleteById(id);
    }

    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    public void deleteUserCourse(Integer userId, Integer courseId) {
        UserCourseId userCourseId = new UserCourseId();
        userCourseId.setUserId(userId);
        userCourseId.setCourseId(courseId);
        userCourseRepository.deleteById(userCourseId);
    }

    public void deleteTrainerCourse(Integer trainerId, Integer courseId) {
        TrainerCourseId trainerCourseId = new TrainerCourseId();
        trainerCourseId.setTrainerId(trainerId);
        trainerCourseId.setCourseId(courseId);
        trainerCourseRepository.deleteById(trainerCourseId);
    }

}
