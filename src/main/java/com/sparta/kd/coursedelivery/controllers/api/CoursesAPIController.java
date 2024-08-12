package com.sparta.kd.coursedelivery.controllers.api;

import com.sparta.kd.coursedelivery.entities.Course;
import com.sparta.kd.coursedelivery.services.CourseDeliveryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/courses/")
public class CoursesAPIController {

    private final CourseDeliveryService courseDeliveryService;

    public CoursesAPIController(CourseDeliveryService courseDeliveryService) {
        this.courseDeliveryService = courseDeliveryService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Course>> getAllCourses() {
        List<EntityModel<Course>> courses = courseDeliveryService.getAllCourses()
                .stream()
                .map(course -> {
                    Link selfLink = WebMvcLinkBuilder.linkTo(
                            methodOn(CoursesAPIController.class).getCourse(course.getId())).withSelfRel();
                    Link relLink = WebMvcLinkBuilder.linkTo(
                            methodOn(CoursesAPIController.class).getAllCourses()).withRel("courses");
                    return EntityModel.of(course, selfLink, relLink);
                })
                .collect(Collectors.toList());
        return CollectionModel.of(courses, WebMvcLinkBuilder.linkTo(methodOn(CoursesAPIController.class).getAllCourses()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Course>> getCourse(@PathVariable Integer id) {
        Course course = courseDeliveryService.getCourse(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Course> courseEntityModel = EntityModel.of(course);
        Link selfLink = WebMvcLinkBuilder.linkTo(
                methodOn(CoursesAPIController.class).getCourse(id)).withSelfRel();
        Link relLink = WebMvcLinkBuilder.linkTo(
                methodOn(CoursesAPIController.class).getAllCourses()).withRel("courses");
        return ResponseEntity.ok(courseEntityModel.add(selfLink, relLink));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Course>> createCourse(@RequestBody Course course) {
        courseDeliveryService.createCourse(course);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CoursesAPIController.class).getCourse(course.getId())).withSelfRel();
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(methodOn(CoursesAPIController.class).getCourse(course.getId())).toUri())
                .body(EntityModel.of(course, selfLink));
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Course>> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        Course existingCourse = courseDeliveryService.getCourse(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        course.setId(id); // Ensure the ID is set for the update
        courseDeliveryService.createCourse(course); // Assuming createCourse handles both creation and update
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CoursesAPIController.class).getCourse(id)).withSelfRel();
        return ResponseEntity.ok(EntityModel.of(course, selfLink));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        Course existingCourse = courseDeliveryService.getCourse(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        courseDeliveryService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
