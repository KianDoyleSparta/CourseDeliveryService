package com.sparta.kd.coursedelivery.controllers.api;

import com.sparta.kd.coursedelivery.entities.Trainer;
import com.sparta.kd.coursedelivery.services.CourseDeliveryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/trainers/")
public class TrainersAPIController {

    private final CourseDeliveryService courseDeliveryService;

    public TrainersAPIController(CourseDeliveryService courseDeliveryService) {
        this.courseDeliveryService = courseDeliveryService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Trainer>> getAllTrainers() {

        List<EntityModel<Trainer>> trainers = courseDeliveryService.getAllTrainers()
                .stream()
                .map(trainer -> {
                    List<Link> courseLinks = trainer.getTrainerCourses()
                            .stream()
                            .map(trainerCourse -> WebMvcLinkBuilder.linkTo(
                                            methodOn(CoursesAPIController.class).getCourse(trainerCourse.getId().getCourseId()))
                                    .withRel(trainerCourse.getCourse().getTitle()))
                            .toList();
                    Link selfLink = WebMvcLinkBuilder.linkTo(
                            methodOn(TrainersAPIController.class).getTrainer(trainer.getId())).withSelfRel();
                    Link relLink = WebMvcLinkBuilder.linkTo(
                            methodOn(TrainersAPIController.class).getAllTrainers()).withRel("trainer");
                    return EntityModel.of(trainer, selfLink, relLink).add(courseLinks);
                })
                .toList();

        return CollectionModel.of(trainers, WebMvcLinkBuilder.linkTo(methodOn(TrainersAPIController.class).getAllTrainers()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Trainer>> getTrainer(@PathVariable Integer id) {

        EntityModel<Trainer> trainerEntityModel = Stream.of(courseDeliveryService.getTrainer(id))
                .map(trainer -> {
                    List<Link> courseLinks = trainer.getTrainerCourses()
                            .stream()
                            .map(trainerCourse -> WebMvcLinkBuilder.linkTo(
                                            methodOn(CoursesAPIController.class).getCourse(trainerCourse.getId().getCourseId()))
                                    .withRel(trainerCourse.getCourse().getTitle()))
                            .toList();
                    Link selfLink = WebMvcLinkBuilder.linkTo(
                            methodOn(TrainersAPIController.class).getTrainer(id)).withSelfRel();
                    Link relLink = WebMvcLinkBuilder.linkTo(
                            methodOn(TrainersAPIController.class).getAllTrainers()).withRel("trainer");
                    return EntityModel.of(trainer, selfLink, relLink).add(courseLinks);
                })
                .findFirst()
                .orElse(null);

        if (trainerEntityModel == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(trainerEntityModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Trainer>> createTrainer(@RequestBody Trainer trainer) {
        courseDeliveryService.createTrainer(trainer);
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(methodOn(TrainersAPIController.class).getTrainer(trainer.getId())).toUri())
                .body(EntityModel.of(trainer, WebMvcLinkBuilder.linkTo(methodOn(TrainersAPIController.class).getTrainer(trainer.getId())).withSelfRel()));
    }

    @PostMapping("{id}")
    public ResponseEntity<EntityModel<Trainer>> updateTrainer(@PathVariable Integer id, @RequestBody Trainer trainer) {
        Trainer trainerEntity = courseDeliveryService.getTrainer(id);
        if (trainerEntity == null) {
            return ResponseEntity.notFound().build();
        }
        trainer.setId(id);
        courseDeliveryService.createTrainer(trainer);
        return ResponseEntity.ok(
                EntityModel.of(courseDeliveryService.getTrainer(id), WebMvcLinkBuilder.linkTo(methodOn(TrainersAPIController.class).getTrainer(id)).withSelfRel()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Integer id) {
        Trainer trainer = courseDeliveryService.getTrainer(id);
        if (trainer == null) {
            return ResponseEntity.notFound().build();
        }
        courseDeliveryService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }
}
