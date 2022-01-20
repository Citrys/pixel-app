package com.cocktail.springboot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jayway.jsonpath.JsonPath;
import com.cocktail.model.Ingredient;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CocktailApplicationRestTests {

	String cocktailId = "";
	String name = "";
	String description = "";

	@Autowired
	private MockMvc mvc;

	@BeforeAll
	public void setTests() {
		 name = RandomString.make(10);
		 description = RandomString.make(10);
	}

	@AfterAll
	public void alterTests() {
		name = "";
		description = "";
	}

	@Test
	public void createCocktail() throws Exception {
		String request = String.format("{\"name\":\"%s\"," +
				"\"description\": \"%s\"," +
				"\"ingredients\": [\"%s\", \"%s\"]}", name, description, Ingredient.GIN, Ingredient.VODKA);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cocktail")
						.content(request)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(201))
				.andReturn();
		cocktailId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
	}

	@Test
	public void getCocktailById() throws Exception {
		String res = String.format("/cocktail/%s", cocktailId);
		mvc.perform(MockMvcRequestBuilders.get(res).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(name));
	}

	@Test
	public void getCocktails() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/cocktails").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}
}
