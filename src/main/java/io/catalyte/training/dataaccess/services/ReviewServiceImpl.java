package io.catalyte.training.dataaccess.services;

import io.catalyte.training.dataaccess.exceptions.BadDataResponse;
import io.catalyte.training.dataaccess.exceptions.ResourceNotFound;
import io.catalyte.training.dataaccess.repositories.ReviewRepository;
import io.catalyte.training.dataaccess.entities.Vehicle;
import io.catalyte.training.dataaccess.entities.Review;
import io.catalyte.training.dataaccess.exceptions.ServiceUnavailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

  @Autowired private ReviewRepository reviewRepository;

  @Autowired private VehicleService vehicleService;

  /**
   * Reads all reviews from the database.
   *
   * @return - a list of all reviews in the database
   */
  public List<Review> getAllReviews(Review review) {
      try {

        if (review.isEmpty()) {
          return reviewRepository.findAll();
        } else {
          Example<Review> reviewExample = Example.of(review);
          return reviewRepository.findAll(reviewExample);
        }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Looks up a review from the database by id.
   *
   * @param id - the id of the review to lookup.
   * @return - the review belonging to the ID or a 404 if not found
   */
  public Review getReview(Long id) {
    try {
      Review reviewLookupResult = reviewRepository.findById(id).orElse(null);
      if (reviewLookupResult != null) {
        return reviewLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the review
    throw new ResourceNotFound("Could not locate a review with the id: " + id);
  }

  /**
   * Writes multiple new reviews to the database.
   *
   * @param reviews - the reviews to write.
   * @return - the list of new reviews
   */
  public List<Review> addReviews(List<Review> reviews) {
    try {
      return reviewRepository.saveAll(reviews);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Writes a new review to the database.
   *
   * @param review - the review to write.
   * @return - the enw review
   */
  public Review addReview(Review review) {
    try {
      return reviewRepository.save(review);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Updates the database information for an existing review. Does not insert a record if the id
   * does not exist.
   *
   * @param id - the id of the review to update.
   * @param review - the supplied review information to update the database record with.
   * @return - the updated review or a 404 if the review was not found
   */
  public Review updateReviewById(Long id, Review review) {

    // first, check to make sure the id passed matches the id in the review passed
    if (!review.getId().equals(id))
    {
      throw new BadDataResponse("Review ID must match the ID specified in the URL");
    }

    try {
      Review reviewFromDb = reviewRepository.findById(id).orElse(null);
      if (reviewFromDb != null) {
        return reviewRepository.save(review);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the review
    throw new ResourceNotFound("Could not locate a review with the id: " + id);
  }

  /**
   * Retrieves all reviews related to a make and model of vehicle.
   *
   * @param make - the make of vehicle that reviews should be related to.
   * @param model - the model of vehicle that reviews should be related to.
   * @return
   */
  public List<Review> findReviewsByMakeAndModel(String make, String model) {

    // create new vehicle to use as an example
    Vehicle vehicleToQuery = new Vehicle(null, make, model, null);

    try {
      List<Vehicle> vehicleQueryResults = vehicleService.queryVehicles(vehicleToQuery);
      List<Long> idList = new ArrayList<>();

      if (!vehicleQueryResults.isEmpty()) {

        vehicleQueryResults.forEach(vehicle -> idList.add(vehicle.getId()));

        return reviewRepository.findReviewsByVehicleIdIn(idList);
      } else {
        return new ArrayList<>();
      }

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Returns only the count of reviews related to a certain make and model combination.
   *
   * @param make - the make of vehicle that reviews should be related to.
   * @param model - the model of vehicle that reviews should be related to.
   * @return
   */
  public Integer getReviewCountByMakeAndModel(String make, String model) {
    List<Review> reviewList = findReviewsByMakeAndModel(make, model);

    return reviewList.size();
  }

  /**
   * Deletes a review from the database by id.
   *
   * @param id - the id of the review to delete from the database.
   */
  public void deleteReview(Long id) {

    try {
      if (reviewRepository.existsById(id)) {
        reviewRepository.deleteById(id);
        return;
      }
    }
    catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the review
    throw new ResourceNotFound("Could not locate a review with the id: " + id);
  }

  /**
   * Deletes reviews from the database by username.
   *
   * @param username - the username of the reviews to delete from the database.
   */
  public void deleteReviewByUsername(String username) {
    try {
      if (reviewRepository.existsByUsername(username)) {
        reviewRepository.deleteByUsername(username);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the review
    throw new ResourceNotFound("Could not locate a review with the user name: " + username);
  }
}
