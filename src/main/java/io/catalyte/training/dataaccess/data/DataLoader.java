package io.catalyte.training.dataaccess.data;

import io.catalyte.training.dataaccess.entities.Review;
import io.catalyte.training.dataaccess.repositories.ReviewRepository;
import io.catalyte.training.dataaccess.entities.Vehicle;
import io.catalyte.training.dataaccess.repositories.VehicleRepository;
import java.sql.Date;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * This class runs after the server starts and executes methods that load initial datasets into the
 * db
 */
@Component
public class DataLoader implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  @Autowired private VehicleRepository vehicleRepository;

  @Autowired private ReviewRepository reviewRepository;

  private Vehicle vehicle1;
  private Vehicle vehicle2;
  private Vehicle vehicle3;

  @Override
  public void run(String... strings) throws Exception {
    logger.info("Loading data...");

    loadVehicles();
    loadReviews();
  }

  private void loadVehicles() {
    vehicle1 = vehicleRepository.save(new Vehicle("Car", "Dodge", "Challenger", 2010));
    vehicle2 =
        vehicleRepository.save(
            new Vehicle("Light Freighter", "Corellian Engineering Corporation", "YT-1300", 3050));
    vehicle3 = vehicleRepository.save(new Vehicle("Rocket", "Space X", "Falcon Heavy", 2015));
  }

  private void loadReviews() {
    reviewRepository.save(
        new Review(
            "Challengers rock!",
            "It's better than a Mustang",
            5,
            Date.from(LocalDate.parse("2010-03-17").atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "theHound",
            vehicle1));
    reviewRepository.save(
        new Review(
            "Eat my dust",
            "Vanishing Point. Enough said.",
            5,
            Date.from(LocalDate.parse("2010-04-12").atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "Steve McQueen",
            vehicle1));
    reviewRepository.save(
        new Review(
            "Need a mechanic, apply within",
            "This thing is great for pushing around cargo. Very customizable, too!",
            5,
            Date.from(LocalDate.parse("2010-05-10").atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "ReBel SpY GuY",
            vehicle2));
    reviewRepository.save(
        new Review(
            "WTB drone ship",
            "This thing is outta this world!",
            5,
            Date.from(LocalDate.parse("2010-06-08").atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "Marvin the Martian",
            vehicle3));
  }
}
