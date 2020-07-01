package io.catalyte.training.dataaccess.controllers;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReviewControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  ResultMatcher okStatus = MockMvcResultMatchers.status().isOk();
  ResultMatcher createdStatus = MockMvcResultMatchers.status().isCreated();
  ResultMatcher deletedStatus = MockMvcResultMatchers.status().isNoContent();
  ResultMatcher notFoundStatus = MockMvcResultMatchers.status().isNotFound();
  ResultMatcher expectedType = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8);

  @Before
  public void setup () {
    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
    this.mockMvc = builder.build();
  }

  @Test
  public void test1_getReviews() throws Exception {

    mockMvc
        .perform(get("/" + "Reviews".toLowerCase()))
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test2_getReview() throws Exception {

    mockMvc
        .perform(get("/" + "Review".toLowerCase() + "s/1"))
        .andExpect(jsonPath("$.title", is("Challengers rock!")))
        .andExpect(okStatus)
        .andExpect(expectedType);
  }

  @Test
  public void test3_postReview() throws Exception {

    String json = "{\"title\":\"Eat my dust\",\"description\":\"Vanishing Point. Enough said.\",\"rating\":5,\"date\":\"2014-09-18\",\"username\":\"Steve McQueen\",\"vehicle\":{\"id\":1,\"type\":\"Car\",\"make\":\"Dodge\",\"model\":\"Challenger\",\"year\":2010}}";

    this.mockMvc
        .perform(post("/" + "Review".toLowerCase() + "s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(jsonPath("$.title", is("Eat my dust")))
        .andExpect(createdStatus)
        .andExpect(expectedType);

  }
}