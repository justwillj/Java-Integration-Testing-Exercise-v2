package io.catalyte.training.dataaccess.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static io.catalyte.training.dataaccess.constants.StringConstants.REQUIRED_FIELD;

@Entity
@Table(name = "vehicles")
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "type" + REQUIRED_FIELD)
  private String type;

  @NotBlank(message = "make" + REQUIRED_FIELD)
  private String make;

  @NotBlank(message = "model" + REQUIRED_FIELD)
  private String model;

  @Positive(message = "year must be greater than 0")
  @NotNull(message = "year" + REQUIRED_FIELD)
  private Integer year;

  @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Review> reviews = new HashSet<>();

  public Vehicle() {}

  public Vehicle(
      @NotBlank String type,
      @NotBlank String make,
      @NotBlank String model,
      @Positive @NotNull Integer year) {
    this.type = type;
    this.make = make;
    this.model = model;
    this.year = year;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return "Vehicle{"
        + "id="
        + id
        + ", type='"
        + type
        + '\''
        + ", make='"
        + make
        + '\''
        + ", model='"
        + model
        + '\''
        + ", year="
        + year
        + '}';

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vehicle vehicle = (Vehicle) o;
    return Objects.equals(id, vehicle.id) &&
            Objects.equals(type, vehicle.type) &&
            Objects.equals(make, vehicle.make) &&
            Objects.equals(model, vehicle.model) &&
            Objects.equals(year, vehicle.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, make, model, year);
  }

  @JsonIgnore
  public boolean isEmpty(){
    return Objects.isNull(id) &&
            Objects.isNull(type) &&
            Objects.isNull(make) &&
            Objects.isNull(model) &&
            Objects.isNull(year);
  }
}
