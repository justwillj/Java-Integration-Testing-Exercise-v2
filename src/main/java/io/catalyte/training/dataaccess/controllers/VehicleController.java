package io.catalyte.training.dataaccess.controllers;

import io.catalyte.training.dataaccess.services.VehicleService;
import io.catalyte.training.dataaccess.entities.Vehicle;
import io.catalyte.training.dataaccess.entities.VehicleStats;
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
@RequestMapping("/vehicles")
public class VehicleController {

  private final Logger logger = LoggerFactory.getLogger(VehicleController.class);

  @Autowired private VehicleService vehicleService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<VehicleStats> getVehicle(@PathVariable Long id) {
    logger.info(new Date() + " Get by id " + id + " request received");

    return new ResponseEntity<>(vehicleService.getVehicle(id), HttpStatus.OK);
  }

  @GetMapping
  ///vehicles?make=honda
  public ResponseEntity<List<Vehicle>> queryVehicles(Vehicle vehicle) {
    logger.info(new Date() + " Query request received: " + vehicle.toString());

    return new ResponseEntity<>(vehicleService.queryVehicles(vehicle), HttpStatus.OK);
  }

  @PostMapping(value = "/all")
  public ResponseEntity<List<Vehicle>> saveAll(@Valid @RequestBody List<Vehicle> vehicles) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(vehicleService.addVehicles(vehicles), HttpStatus.CREATED);
  }

  @PostMapping
  public ResponseEntity<Vehicle> save(@Valid @RequestBody Vehicle vehicle) {
    logger.info(new Date() + " Post request received");

    return new ResponseEntity<>(vehicleService.addVehicle(vehicle), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Vehicle> updateVehicleById(
      @PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
    logger.info(new Date() + " Put request received for id: " + id);

    return new ResponseEntity<>(vehicleService.updateVehicleById(id, vehicle), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity deleteVehicle(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);

    vehicleService.deleteVehicle(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
