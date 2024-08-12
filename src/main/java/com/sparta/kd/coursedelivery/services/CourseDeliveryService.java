package com.sparta.kd.coursedelivery.services;

import com.sparta.kd.coursedelivery.entities.*;
import com.sparta.kd.coursedelivery.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer getTrainer(Integer id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<UserCourse> getAllUserCourses() {
        return userCourseRepository.findAll();
    }

    public UserCourse getUserCourse(Integer userId, Integer courseId) {
        UserCourseId userCourseID = new UserCourseId();
        userCourseID.setUserId(userId);
        userCourseID.setCourseId(courseId);
        return userCourseRepository.findById(userCourseID).orElse(null);
    }

    public List<TrainerCourse> getAllTrainerCourses() {
        return trainerCourseRepository.findAll();
    }

    public TrainerCourse getTrainerCourse(Integer id) {
        TrainerCourseId trainerCourseId = new TrainerCourseId();
        trainerCourseId.setTrainerId(id);
        trainerCourseId.setCourseId(id);
        return trainerCourseRepository.findById(trainerCourseId).orElse(null);
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
