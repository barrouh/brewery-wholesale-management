package com.barrouh.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BeerRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void beersByBrewery() throws Exception {
		this.mockMvc.perform(get("/api/v1/beers/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Leffe Blonde")));
	}

	@Test
	void addBeer() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/beers/").contentType(MediaType.APPLICATION_JSON).content(
						"{\"id\": 10,\"name\": \"Test Add\",\"alcoholPercentage\": 5,\"price\": 3,\"breweryId\": 1}"))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().string(containsString("Test Add")));
	}
	
	@Test
	void addBeerKo() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/beers/").contentType(MediaType.APPLICATION_JSON).content(
						"{\"id\": 10,\"name\": \"Test Add\",\"alcoholPercentage\": 5,\"price\": 3,\"breweryId\": 10}"))
				.andDo(print()).andExpect(status().isNotFound()).andExpect(content().string(containsString("Brewery with Id 10 not found.")));
	}

	@Test
	void deleteBeer() throws Exception {
		this.mockMvc.perform(delete("/api/v1/beers/10")).andDo(print()).andExpect(status().isAccepted());
	}

	@Test
	void deleteBeerKO() throws Exception {
		this.mockMvc.perform(delete("/api/v1/beers/111")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Beer with Id 111 not found.")));
	}

}