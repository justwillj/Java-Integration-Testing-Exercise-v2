package io.catalyte.training.dataaccess.services;

import io.catalyte.training.dataaccess.entities.Review;

import java.util.List;

public interface ReviewService {

  List<Review> getAllReviews(Review review);

  Review getReview(Long id);

  List<Review> findReviewsByMakeAndModel(String make, String model);

  Integer getReviewCountByMakeAndModel(String make, String model);

  List<Review> addReviews(List<Review> reviews);

  Review addReview(Review review);

  Review updateReviewById(Long id, Review review);

  void deleteReview(Long id);

  void deleteReviewByUsername(String username);
}
