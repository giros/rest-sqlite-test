package com.giros.rest.sqlite.test;

import com.giros.rest.sqlite.test.model.Aircraft;
import com.giros.rest.sqlite.test.repository.AircraftRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql("/scripts/db-init.sql")
class RestSqliteTestApplicationTests {

	@Autowired
	AircraftRepository aircraftRepository;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	@Disabled
	void saveAircraftTest() {
		Aircraft savedAircraft = aircraftRepository.save(new Aircraft("B737", "Boeing", "737"));
		Aircraft returnedAircraft = aircraftRepository.findByCode("B737");

		assertNotNull(returnedAircraft);
		assertEquals(savedAircraft, returnedAircraft);
	}

	@Test
	void addAircraftTest() {
		ResponseEntity<Aircraft> responseEntity =
			testRestTemplate.postForEntity(
				"/aircraft",
				Aircraft.builder()
					.withCode("B737")
					.withManufacturer("Boeing")
					.withModel("737")
					.withDescription("Boeing 737")
					.build(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());

		Aircraft newAircraft = responseEntity.getBody();

		assertNotNull(newAircraft);
		assertNotNull(newAircraft.getId());
		assertNotNull(newAircraft.getStartDate());
	}

	@Test
	void addAircraftFailTest() {
		ResponseEntity<String> responseEntity =
			testRestTemplate.postForEntity(
				"/aircraft",
				Aircraft.builder()
					.withId("illegal")
					.withCode("B737")
					.withManufacturer("Boeing")
					.withModel("737")
					.withDescription("Boeing 737")
					.build(),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
	}

	@Test
	void getAircraftTest() {
		ResponseEntity<Aircraft> postResponseEntity =
			testRestTemplate.postForEntity(
				"/aircraft",
				Aircraft.builder()
					.withCode("B737")
					.withManufacturer("Boeing")
					.withModel("737")
					.withDescription("Boeing 737")
					.build(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), postResponseEntity.getStatusCode().value());

		Aircraft newAircraft = postResponseEntity.getBody();

		assertNotNull(newAircraft);
		assertNotNull(newAircraft.getId());

		ResponseEntity<Aircraft> getResponseEntity =
			testRestTemplate.getForEntity(
				"/aircraft/" + newAircraft.getId(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), getResponseEntity.getStatusCode().value());

		Aircraft airCraftById = getResponseEntity.getBody();

		assertNotNull(airCraftById);
		assertEquals(newAircraft, airCraftById);
	}

	@Test
	void updateAircraftTest() {
		ResponseEntity<Aircraft> postResponseEntity =
			testRestTemplate.postForEntity(
				"/aircraft",
				Aircraft.builder()
					.withCode("B737")
					.withManufacturer("Boeing")
					.withModel("737")
					.withDescription("Boeing 737")
					.build(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), postResponseEntity.getStatusCode().value());

		Aircraft newAircraft = postResponseEntity.getBody();

		assertNotNull(newAircraft);
		assertNotNull(newAircraft.getId());

		newAircraft.setDescription("Boeing 737 updated description");

		testRestTemplate.put(
			"/aircraft",
			newAircraft,
			Aircraft.class);

		ResponseEntity<Aircraft> getResponseEntity =
			testRestTemplate.getForEntity(
				"/aircraft/" + newAircraft.getId(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), getResponseEntity.getStatusCode().value());

		Aircraft airCraftById = getResponseEntity.getBody();

		assertNotNull(airCraftById);
		assertEquals(newAircraft, airCraftById);
	}

	@Test
	void updateAircraftFailTest() {
		ResponseEntity<Aircraft> postResponseEntity =
			testRestTemplate.postForEntity(
				"/aircraft",
				Aircraft.builder()
					.withCode("B737")
					.withManufacturer("Boeing")
					.withModel("737")
					.withDescription("Boeing 737")
					.build(),
				Aircraft.class);

		assertEquals(HttpStatus.OK.value(), postResponseEntity.getStatusCode().value());

		Aircraft newAircraft = postResponseEntity.getBody();

		assertNotNull(newAircraft);
		assertNotNull(newAircraft.getId());

		newAircraft.setId("nonexisting");

		ResponseEntity<String> responseEntity =
			testRestTemplate.exchange(
				"/aircraft",
				HttpMethod.PUT,
				new HttpEntity<>(newAircraft),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
	}

}
