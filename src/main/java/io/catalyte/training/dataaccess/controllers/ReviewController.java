package io.catalyte.training.dataaccess.controllers;

import io.catalyte.training.dataaccess.services.ReviewService;
import io.catalyte.training.dataaccess.entities.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

  @Autowired private ReviewService reviewService;

  @GetMapping
  public ResponseEntity<List<Review>> getReviews(Review review) {
    logger.info(new Date() + " Get all request received");

    return new ResponseEntity<>(reviewService.getAllReviews(review), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Review> getReview(@PathVariable Long id) {
    logger.info(new Date() + " Get by id request received: " + "id: " + id);

    return new ResponseEntity<>(reviewService.getReview(id), HttpStatus.OK);
  }

  @GetMapping(value = "/make/{make}/model/{model}")
  public ResponseEntity<List<Review>> getReviewByMakeAndModel(
      @PathVariable String make, @PathVariable String model) {
    logger.info(
        new Date()
            + " Get by make and model request received: "
            + "make: "
            + make
            + ", model: "
            + model);

    return new ResponseEntity<>(
        reviewService.findReviewsByMakeAndModel(make, model), HttpStatus.OK);
  }

  @GetMapping(value = "/make/{make}/model/{model}/count")
  public ResponseEntity<Integer> getReviewCountByMakeAndModel(
      @PathVariable String make, @PathVariable String model) {
    logger.info(
        new Date()
            + " Get count by make and model request received: "
            + "make: "
            + make
            + ", model: "
            + model);

    return new ResponseEntity<>(
        reviewService.getReviewCountByMakeAndModel(make, model), HttpStatus.OK);
  }

  @PostMapping(value = "/all")
  public ResponseEntity<List<Review>> saveAll(@Valid @RequestBody List<Review> reviews) {
    logger.info(new Date() + " Post request received " + reviews.toString());

    return new ResponseEntity<>(reviewService.addReviews(reviews), HttpStatus.CREATED);
  }

  @PostMapping
  public ResponseEntity<Review> save(@Valid @RequestBody Review review) {
    logger.info(new Date() + " Post request received " + review.toString());

    return new ResponseEntity<>(reviewService.addReview(review), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Review> updateReviewById(
      @PathVariable Long id, @Valid @RequestBody Review Review) {
    logger.info(new Date() + " Update request received for id: " + id);

    return new ResponseEntity<>(reviewService.updateReviewById(id, Review), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteReviewById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    reviewService.deleteReview(id);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(value = "/username/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReviewByUsername(@PathVariable String username) {
    logger.info(new Date() + " Delete request received for username: " + username);

    reviewService.deleteReviewByUsername(username);
  }
}
