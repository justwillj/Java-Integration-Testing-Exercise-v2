package io.catalyte.training.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

  @Test
  void getPet() {
  }
}