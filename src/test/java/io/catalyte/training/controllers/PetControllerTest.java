package io.catalyte.training.controllers;

import static io.catalyte.training.constants.StringConstants.CONTEXT_PETS;
import static io.catalyte.training.constants.StringConstants.CONTEXT_VACCINATIONS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.entities.Pet;
import io.catalyte.training.services.PetService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher badRequestStatus = MockMvcResultMatchers.status().isBadRequest();
  ResultMatcher dataErrorStatus = MockMvcResultMatchers.status().isServiceUnavailable();
  ResultMatcher serverErrorStatus = MockMvcResultMatchers.status().isInternalServerError();
  ResultMatcher expectedType = MockMvcResultMatchers.content()
      .contentType(MediaType.APPLICATION_JSON);

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;


  @BeforeEach
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();

  }

  @Test
  void getPetsReturnsThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PETS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(3)));
  }
  @Test
  void getPetThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PETS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.name", is("Cletus")));
  }


  @Test
  void saveAllPets() throws Exception{
    List<Pet> json = new ArrayList<>();
    Pet pet1 = new Pet("Cletus", "Dog", 6);
    Pet pet2 = new Pet("Alexander Bunnington", "Rabbit", 3);
    json.add(pet1);
    json.add(pet2);

    String jsonAsString = mapper.writeValueAsString(json);

    this.mockMvc
        .perform(post(CONTEXT_PETS + "/all")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAsString))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", isA(ArrayList.class)))
        .andExpect(jsonPath("$", hasSize(2)));

  }
  @Test
  void postNewPet() throws Exception {
    Pet pet1 = new Pet("Cletus", "Dog", 6);
    String petAsString = mapper.writeValueAsString(pet1);

    this.mockMvc
        .perform(post(CONTEXT_PETS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(petAsString))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.breed", is("Dog")));
  }

  @Test
  void putPet() throws Exception {
    Pet pet1 = new Pet("Cletus", "Dog", 6);
    pet1.setId(1L);
    String petAsString = mapper.writeValueAsString(pet1);
    this.mockMvc
        .perform(put(CONTEXT_PETS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(petAsString))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.age", is(6)));

  }


  @Test
  void deletePet() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_PETS + "/3"))
        .andExpect(deletedStatus);
  }

  @Test
  public void getPetBadRequest() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PETS + "/NOTVALID"))
        .andExpect(badRequestStatus);
  }

  @Test
  public void getPetThatDoesNotExist() throws Exception {
    mockMvc
        .perform(get(CONTEXT_PETS + "/4567890"))
        .andExpect(notFoundStatus);
  }

}