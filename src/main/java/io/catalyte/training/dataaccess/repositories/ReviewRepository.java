package io.catalyte.training.dataaccess.repositories;

import io.catalyte.training.dataaccess.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findReviewsByVehicleIdIn(List<Long> ids);

  Integer countByVehicleId(List<Long> ids);

  boolean existsByUsername(String username);

  @Transactional
  void deleteByUsername(String username);
}
