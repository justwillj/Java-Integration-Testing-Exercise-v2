package io.catalyte.training.dataaccess.services;

import io.catalyte.training.dataaccess.exceptions.BadDataResponse;
import io.catalyte.training.dataaccess.exceptions.ResourceNotFound;
import io.catalyte.training.dataaccess.repositories.ReviewRepository;
import io.catalyte.training.dataaccess.repositories.VehicleRepository;
import io.catalyte.training.dataaccess.entities.Vehicle;
import io.catalyte.training.dataaccess.entities.VehicleStats;
import io.catalyte.training.dataaccess.exceptions.ServiceUnavailable;
import org.hibernate.JDBCException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.catalyte.training.dataaccess.constants.StringConstants.ID_NOT_FOUND;

@Service
public class VehicleServiceImpl implements VehicleService {

  private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

  @Autowired private VehicleRepository vehicleRepository;
  @Autowired private ReviewRepository reviewRepository;

  /**
   * This method accepts vehicles as an optional parameter. When it is supplied, we create a query
   * by example. Otherwise, we get all vehicles.
   *
   * @param vehicle - any provided fields will be converted to an exact match AND query
   * @return
   */
  public List<Vehicle> queryVehicles(Vehicle vehicle) {
    try {
      if (vehicle.isEmpty()) {
        return vehicleRepository.findAll();
      } else {
        Example<Vehicle> vehicleExample = Example.of(vehicle);
        return vehicleRepository.findAll(vehicleExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Lookup a vehicle by id.
   *
   * @param id - the id to lookup
   * @return
   */
  public VehicleStats getVehicle(Long id) {

    try {
      Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

      if (vehicle != null) {
        List<Long> idList = new ArrayList<Long>();//Collections.singletonList(vehicle.getId());
        idList.add(id);
        int numOfReviews = reviewRepository.countByVehicleId(idList);
        return new VehicleStats(
                vehicle.getId(),
                vehicle.getType(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                numOfReviews);
      }
    } catch (Exception e){
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the vehicle
    throw new ResourceNotFound("Could not locate a vehicle with the id: " + id);
  }

  /**
   * Writes multiple new vehicles to the database.
   *
   * @param vehicles - the vehicles to write.
   * @return
   */
  public List<Vehicle> addVehicles(List<Vehicle> vehicles) {
    try {
      return vehicleRepository.saveAll(vehicles);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Writes a new vehicle to the database.
   *
   * @param vehicle - the vehicle to write.
   * @return
   */
  public Vehicle addVehicle(Vehicle vehicle) {
    try {
      return vehicleRepository.save(vehicle);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * Update an existing vehicle in the database.
   *
   * @param id - the id of the vehicle to update.
   * @param vehicle - the vehicle information to update.
   * @return
   */
  public Vehicle updateVehicleById(Long id, Vehicle vehicle) {

    // first, check to make sure the id passed matches the id in the vehicle passed
    if (!vehicle.getId().equals(id))
    {
      throw new BadDataResponse("Vehicle ID must match the ID specified in the URL");
    }

    try {
      Vehicle vehicleFromDb = vehicleRepository.findById(id).orElse(null);

      if (vehicleFromDb != null) {
        return vehicleRepository.save(vehicle);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the vehicle
    throw new ResourceNotFound("Could not locate a vehicle with the id: " + id);

  }

  /**
   * Delete a vehicle from the database.
   *
   * @param id - the id of the vehicle to be deleted.
   */
  public void deleteVehicle(Long id) {

    try {
      if (vehicleRepository.existsById(id)) {
        vehicleRepository.deleteById(id);
        return;
      }
    }
    catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this pint, we did not find the vehicle
    throw new ResourceNotFound("Could not locate a vehicle with the id: " + id);
  }
}
