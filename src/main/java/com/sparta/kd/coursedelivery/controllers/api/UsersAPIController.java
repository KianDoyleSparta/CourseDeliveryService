package com.sparta.kd.coursedelivery.controllers.api;

import com.sparta.kd.coursedelivery.entities.User;
import com.sparta.kd.coursedelivery.services.CourseDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/users/")
public class UsersAPIController {

    private final CourseDeliveryService courseDeliveryService;

    public UsersAPIController(CourseDeliveryService courseDeliveryService) {
        this.courseDeliveryService = courseDeliveryService;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {

        List<EntityModel<User>> users = courseDeliveryService.getAllUsers()
                .stream()
                .map(
                        user ->
                        {
                            List<Link> courseLinks =
                                    user.getUserCourses()
                                            .stream()
                                            .map(
                                                    course -> WebMvcLinkBuilder.linkTo(
                                                            methodOn(CourseDeliveryService.class).getCourse(course.getId().getCourseId())).withRel(course.getCourse().getTitle()))
                                            .toList();
                            Link selfLink = WebMvcLinkBuilder.linkTo(
                                    methodOn(UsersAPIController.class).getAllUsers()).withSelfRel();
                            Link relLink = WebMvcLinkBuilder.linkTo(
                                    methodOn(UsersAPIController.class).getAllUsers()).withRel("user");
                            return EntityModel.of(user, selfLink, relLink).add(courseLinks);
                        })
                .toList();
        return CollectionModel.of(users, WebMvcLinkBuilder.linkTo(methodOn(UsersAPIController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable Integer id) {

        EntityModel<User> userEntityModel = Stream.of(courseDeliveryService.getUser(id))
                .map(
                        user ->
                        {
                            List<Link> courseLinks =
                                    user.getUserCourses()
                                            .stream()
                                            .map(
                                                    course -> WebMvcLinkBuilder.linkTo(
                                                            methodOn(CourseDeliveryService.class).getCourse(course.getId().getCourseId())).withRel(course.getCourse().getTitle()))
                                            .toList();
                            Link selfLink = WebMvcLinkBuilder.linkTo(
                                    methodOn(UsersAPIController.class).getUser(id)).withSelfRel();
                            Link relLink = WebMvcLinkBuilder.linkTo(
                                    methodOn(UsersAPIController.class).getAllUsers()).withRel("user");
                            return EntityModel.of(user, selfLink, relLink).add(courseLinks);
                        })
                .toList().getFirst();
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        courseDeliveryService.createUser(user);
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(methodOn(UsersAPIController.class).getUser(user.getId())).toUri())
                .body(EntityModel.of(user, WebMvcLinkBuilder.linkTo(methodOn(UsersAPIController.class).getUser(user.getId())).withSelfRel()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User userEntity = courseDeliveryService.getUser(id);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        courseDeliveryService.createUser(user);
        return ResponseEntity.ok(
                EntityModel.of(courseDeliveryService.getUser(id), WebMvcLinkBuilder.linkTo(methodOn(UsersAPIController.class).getUser(id)).withSelfRel()));
    }

}
