package io.catalyte.training.dataaccess.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

import static io.catalyte.training.dataaccess.constants.StringConstants.*;

@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "title" + REQUIRED_FIELD)
  private String title;

  @NotBlank(message = "description" + REQUIRED_FIELD)
  private String description;

  @Positive(message = "rating" + INVALID_POSITIVE)
  @NotNull
  private Integer rating;

  @NotNull(message = "date" + REQUIRED_FIELD)
  @JsonFormat(pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  @NotBlank(message = "username" + REQUIRED_FIELD)
  private String username;

  @ManyToOne
  @NotNull
  private Vehicle vehicle;

  public Review() {}

  public Review(
      @NotBlank(message = "Title" + REQUIRED_FIELD) String title,
      @NotBlank(message = "Description" + REQUIRED_FIELD) String description,
      @Positive(message = "Rating" + INVALID_POSITIVE) @NotNull int rating,
      @NotBlank(message = "Date" + REQUIRED_FIELD) Date date,
      @NotBlank(message = "Username" + REQUIRED_FIELD) String username,
      @NotNull(message = "Vehicle" + REQUIRED_FIELD) Vehicle vehicle) {
    this.title = title;
    this.description = description;
    this.rating = rating;
    this.date = date;
    this.username = username;
    this.vehicle = vehicle;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  @Override
  public String toString() {
    return "Review{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", rating="
        + rating
        + ", date='"
        + date
        + '\''
        + ", username='"
        + username
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Review review = (Review) o;
    return Objects.equals(id, review.id) &&
            Objects.equals(title, review.title) &&
            Objects.equals(description, review.description) &&
            Objects.equals(rating, review.rating) &&
            Objects.equals(date, review.date) &&
            Objects.equals(username, review.username) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, rating, date, username);
  }

  @JsonIgnore
  public boolean isEmpty(){
    return Objects.isNull(id) &&
            Objects.isNull(title) &&
            Objects.isNull(description) &&
            Objects.isNull(rating) &&
            Objects.isNull(date) &&
            Objects.isNull(username) &&
            Objects.isNull(vehicle);
  }
}
