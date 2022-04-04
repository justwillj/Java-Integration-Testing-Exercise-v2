package io.catalyte.training.controllers;


import static io.catalyte.training.constants.StringConstants.CONTEXT_VACCINATIONS;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
public class VaccinationControllerTest {

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher badRequestStatus = MockMvcResultMatchers.status().isBadRequest();
  ResultMatcher dataErrorStatus = MockMvcResultMatchers.status().isServiceUnavailable();
  ResultMatcher serverErrorStatus = MockMvcResultMatchers.status().isInternalServerError();
  ResultMatcher expectedType = MockMvcResultMatchers.content()
      .contentType(MediaType.APPLICATION_JSON);
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();

  }

  @Test
  public void getVaccinationsReturnsAtLeastThree() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(greaterThan(3))));
  }

  @Test
  public void getVaccinationThatDoesExistById() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS + "/1"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.innoculation", is("Rabies")));
  }

  @Test
  public void getVaccinationThatDoesExistByBreed() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS + "/breed/Dog"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", hasSize(greaterThan(1))));
  }

  @Test
  public void getVaccinationCountByBreed() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS + "/breed/Dog/count"))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", is(greaterThan(1))));
  }


  @Test
  public void postNewVaccination() throws Exception {
    String json = "{\"innoculation\":\"Rabies\",\"date\":\"2019-10-17\",\"pet\":{\"id\":1,\"name\":\"Cletus\",\"breed\":\"Dog\",\"age\":6}}";
    this.mockMvc
        .perform(post(CONTEXT_VACCINATIONS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.innoculation", is("Rabies")));
  }

  @Test
  public void saveAllVaccinations() throws Exception {
    List<String> json = new ArrayList<>();
    json.add(
        "{\"innoculation\":\"Rabies\",\"date\":\"2021-10-17\",\"pet\":{\"id\":1,\"name\":\"Cletus\",\"breed\":\"Dog\",\"age\":6}}");
    json.add(
        "{\"innoculation\":\"Worms\",\"date\":\"2021-04-10\",\"pet\":{\"id\":1,\"name\":\"Cletus\",\"breed\":\"Dog\",\"age\":6}}");

    this.mockMvc
        .perform(post(CONTEXT_VACCINATIONS + "/all")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(json)))
        .andExpect(createdStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$", isA(ArrayList.class)))
        .andExpect(jsonPath("$", hasSize(2)));
  }


  @Test
  public void putVaccination() throws Exception {
    String json = "{\"id\":1,\"innoculation\":\"Rabies\",\"date\":\"2021-02-23\",\"pet\":{\"id\":1,\"name\":\"Cletus\",\"breed\":\"Dog\",\"age\":6}}";
    this.mockMvc
        .perform(put(CONTEXT_VACCINATIONS + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(okStatus)
        .andExpect(expectedType)
        .andExpect(jsonPath("$.date", is("2021-02-23")));
  }


  @Test
  public void deleteVaccination() throws Exception {
    mockMvc
        .perform(delete(CONTEXT_VACCINATIONS + "/3"))
        .andExpect(deletedStatus);
  }

  @Test
  public void getVaccinationBadRequest() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS + "/garbage"))
        .andExpect(badRequestStatus);
  }

  @Test
  public void getVaccinationThatDoesNotExist() throws Exception {
    mockMvc
        .perform(get(CONTEXT_VACCINATIONS + "/4567890"))
        .andExpect(notFoundStatus);
  }

}