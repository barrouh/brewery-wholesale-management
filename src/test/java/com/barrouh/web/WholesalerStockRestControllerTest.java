package com.barrouh.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
class WholesalerStockRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void saveWholesalerStock() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/add").contentType(MediaType.APPLICATION_JSON)
						.content("{\"wholesalerId\": 1,\"beerId\": 3,\"quantity\": 658}"))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().string(containsString("658")));
	}

	@Test
	void saveWholesalerStockWholesalerKo() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/add").contentType(MediaType.APPLICATION_JSON)
						.content("{\"wholesalerId\": 111,\"beerId\": 3,\"quantity\": 658}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Wholesaler with Id 111 not found.")));
	}

	@Test
	void saveWholesalerStockBeerKo() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/add").contentType(MediaType.APPLICATION_JSON)
						.content("{\"wholesalerId\": 1,\"beerId\": 333,\"quantity\": 658}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Beer with Id 333 not found.")));
	}

	@Test
	void updateWholesalerStock() throws Exception {
		this.mockMvc
				.perform(patch("/api/v1/wholesalers-stock/update").contentType(MediaType.APPLICATION_JSON)
						.content("{\"wholesalerId\": 1,\"beerId\": 1,\"quantity\": 555}"))
				.andDo(print()).andExpect(status().isAccepted()).andExpect(content().string(
						containsString("Wholesaler Stock with Id [wholesalerId=1, beerId=1] has been updated.")));
	}

	@Test
	void updateWholesalerStockKo() throws Exception {
		this.mockMvc
				.perform(patch("/api/v1/wholesalers-stock/update").contentType(MediaType.APPLICATION_JSON)
						.content("{\"wholesalerId\": 1,\"beerId\": 4,\"quantity\": 555}"))
				.andDo(print()).andExpect(status().isNotFound()).andExpect(content()
						.string(containsString("Wholesaler Stock with Id [wholesalerId=1, beerId=4] not found.")));
	}

	@Test
	void orderNoDiscount() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
						"{\"wholesalerId\": 1,\"beers\": [{\"id\": 1,\"quantity\": 5},{\"id\": 2,\"quantity\": 5}]}"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(
						"{\"wholesaler\":{\"id\":1,\"name\":\"GeneDrinks\"},\"total\":21.00,\"totalAfterDiscount\":21.00,\"totalQuantity\":10,\"beers\":[{\"id\":1,\"name\":\"Leffe Blonde\",\"price\":2.20,\"quantity\":5},{\"id\":2,\"name\":\"Ardenne Saison\",\"price\":2.00,\"quantity\":5}]}")));
	}

	@Test
	void order20Discount() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
						"{\"wholesalerId\": 1,\"beers\": [{\"id\": 1,\"quantity\": 10},{\"id\": 2,\"quantity\": 12}]}"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(
						"{\"wholesaler\":{\"id\":1,\"name\":\"GeneDrinks\"},\"total\":46.00,\"totalAfterDiscount\":36.800,\"totalQuantity\":22,\"beers\":[{\"id\":1,\"name\":\"Leffe Blonde\",\"price\":2.20,\"quantity\":10},{\"id\":2,\"name\":\"Ardenne Saison\",\"price\":2.00,\"quantity\":12}]}")));
	}

	@Test
	void order10Discount() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
						"{\"wholesalerId\": 1,\"beers\": [{\"id\": 1,\"quantity\": 5},{\"id\": 2,\"quantity\": 11}]}"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(
						"{\"wholesaler\":{\"id\":1,\"name\":\"GeneDrinks\"},\"total\":33.00,\"totalAfterDiscount\":29.700,\"totalQuantity\":16,\"beers\":[{\"id\":1,\"name\":\"Leffe Blonde\",\"price\":2.20,\"quantity\":5},{\"id\":2,\"name\":\"Ardenne Saison\",\"price\":2.00,\"quantity\":11}]}")));
	}

	@Test
	void orderKoOrderEmpty() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("The order cannot be empty")));
	}
	
	@Test
	void orderKoOrderWholesalerIdEmpty() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content("{\"beers\": [{\"id\": 1,\"quantity\": 10},{\"id\": 2,\"quantity\": 12}]}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("The order cannot be empty")));
	}

	@Test
	void orderKoBeerDuplicated() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
						"{\"wholesalerId\": 1,\"beers\": [{\"id\": 1,\"quantity\": 10},{\"id\": 1,\"quantity\": 10}]}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("There can't be any duplicate in the order")));
	}

	@Test
	void orderKoWholesalerNotExist() throws Exception {
		this.mockMvc.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
				"{\"wholesalerId\": 122,\"beers\": [{\"id\": 1,\"quantity\": 10},{\"id\": 2,\"quantity\": 10}]}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("The wholesaler must exist")));
	}

	@Test
	void orderKoWholesalerStockNotExist() throws Exception {
		this.mockMvc
				.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
						"{\"wholesalerId\": 1,\"beers\": [{\"id\": 4,\"quantity\": 10},{\"id\": 2,\"quantity\": 10}]}"))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("The beer must be sold by the wholesaler")));
	}

	@Test
	void orderKoQuantityGreaterThenStock() throws Exception {
		this.mockMvc.perform(post("/api/v1/wholesalers-stock/order").contentType(MediaType.APPLICATION_JSON).content(
				"{\"wholesalerId\": 1,\"beers\": [{\"id\": 1,\"quantity\": 1000},{\"id\": 2,\"quantity\": 10}]}"))
				.andDo(print()).andExpect(status().isNotFound()).andExpect(content().string(
						containsString("The number of beers ordered cannot be greater than the wholesaler's stock")));
	}

}