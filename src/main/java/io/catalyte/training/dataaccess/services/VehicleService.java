package io.catalyte.training.dataaccess.services;

import io.catalyte.training.dataaccess.entities.Vehicle;
import io.catalyte.training.dataaccess.entities.VehicleStats;

import java.util.List;

public interface VehicleService {

  VehicleStats getVehicle(Long id);

  List<Vehicle> queryVehicles(Vehicle vehicle);

  Vehicle addVehicle(Vehicle vehicle);

  List<Vehicle> addVehicles(List<Vehicle> vehicles);

  Vehicle updateVehicleById(Long id, Vehicle vehicle);

  void deleteVehicle(Long id);
}
