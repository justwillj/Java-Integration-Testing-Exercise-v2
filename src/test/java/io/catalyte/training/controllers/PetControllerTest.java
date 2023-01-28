package io.catalyte.training.controllers;

import static io.catalyte.training.constants.StringConstants.CONTEXT_PETS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class PetControllerTest {

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
}