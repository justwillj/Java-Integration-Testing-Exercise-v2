package io.catalyte.training.controllers;

import static io.catalyte.training.constants.StringConstants.CONTEXT_PETS;
import static io.catalyte.training.constants.StringConstants.CONTEXT_VACCINATIONS;
import static io.catalyte.training.constants.StringConstants.LOGGER_REQUEST_BY_BREED_RECEIVED;
import static io.catalyte.training.constants.StringConstants.LOGGER_REQUEST_RECEIVED;
import static io.catalyte.training.constants.StringConstants.SERVER_ERROR;
import static io.catalyte.training.constants.StringConstants.SERVICE_UNAVAILABLE;

import io.catalyte.training.exceptions.ExceptionResponse;
import io.catalyte.training.exceptions.ServiceUnavailable;
import io.catalyte.training.services.VaccinationService;
import io.catalyte.training.entities.Vaccination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * This controller holds CRUD methods for the Pet entity
 */
@RestController
@RequestMapping(CONTEXT_VACCINATIONS)
@ApiResponses(value ={
    @ApiResponse(code=500, message=SERVER_ERROR, response = ExceptionResponse.class),
    @ApiResponse(code=503, message=SERVICE_UNAVAILABLE, response = ServiceUnavailable.class)
})
public class VaccinationController {

  private final Logger logger = LoggerFactory.getLogger(VaccinationController.class);

  @Autowired private VaccinationService VaccinationService;

  /**
   * gives me all vaccinations if I pass a null vaccination or vaccinations matching an example with non-null
   * vaccination
   *
   * @param vaccination vaccination object which can have null or non-null fields, returns status 200
   * @return List of vaccinations
   * @throws Exception
   */
  @GetMapping
  @ApiOperation("Gets all vaccinations, or all vaccinations matching an example with vaccination fields")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Vaccination.class)
  })
  public ResponseEntity<List<Vaccination>> getVaccinations(Vaccination vaccination) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED);

    return new ResponseEntity<>(VaccinationService.getAllVaccinations(vaccination), HttpStatus.OK);
  }

  /**
   * Gets Vaccination by id.
   *
   * @param id the Vaccination id from the path variable
   * @return the Vaccination with said id
   */
  @GetMapping(value = "/{id}")
  @ApiOperation("Finds a Vaccination by Id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = Vaccination.class),
      @ApiResponse(code = 404, message = "NOT FOUND")
  })
  public ResponseEntity<Vaccination> getVaccination(@PathVariable Long id) {
    logger.info(new Date() + LOGGER_REQUEST_RECEIVED + id);

    return new ResponseEntity<>(VaccinationService.getVaccination(id), HttpStatus.OK);
  }

  /**
   * Gets Vaccination by id.
   *
   * @param breed the Vaccination id from the path variable
   * @return the Vaccination with said id
   */
  @GetMapping(value = "/breed/{breed}")
  public ResponseEntity<List<Vaccination>> getVaccinationByBreed(@PathVariable String breed) {
    logger.info(new Date() + LOGGER_REQUEST_BY_BREED_RECEIVED + breed);
    return new ResponseEntity<>(VaccinationService.findVaccinationsByBreed(breed), HttpStatus.OK);
  }

  @GetMapping(value = "/breed/{breed}/count")
  public ResponseEntity<Integer> getVaccinationCountByBreed(@PathVariable String breed) {
    logger.info(new Date()+ LOGGER_REQUEST_BY_BREED_RECEIVED+ breed);
    return new ResponseEntity<>(VaccinationService.getVaccinationCountByBreed(breed), HttpStatus.OK);
  }

  @PostMapping(value = "/all")
  public ResponseEntity<List<Vaccination>> saveAll(@Valid @RequestBody List<Vaccination> Vaccinations) {
    logger.info(new Date() + " Post request received " + Vaccinations.toString());
    return new ResponseEntity<>(VaccinationService.addVaccinations(Vaccinations), HttpStatus.CREATED);
  }

  @PostMapping
  public ResponseEntity<Vaccination> save(@Valid @RequestBody Vaccination Vaccination) {
    logger.info(new Date() + " Post request received " + Vaccination.toString());
    return new ResponseEntity<>(VaccinationService.addVaccination(Vaccination), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Vaccination> updateVaccinationById(@PathVariable Long id, @Valid @RequestBody Vaccination Vaccination)
  {
    logger.info(new Date() + " Update request received for id: " + id);
    return new ResponseEntity<>(VaccinationService.updateVaccinationById(id, Vaccination), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteVaccinationById(@PathVariable Long id) {
    logger.info(new Date() + " Delete request received for id: " + id);
    VaccinationService.deleteVaccination(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }


}
