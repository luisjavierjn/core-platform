package com.inditex.coreplatform.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inditex.coreplatform.utils.ApplicableRangeAggregator;
import com.inditex.coreplatform.utils.PriceRequestAggregator;
import com.inditex.coreplatform.domain.dto.ApplicableRangeDTO;
import com.inditex.coreplatform.domain.requests.CreatePriceRequest;
import com.inditex.coreplatform.repositories.PriceRepository;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PriceControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PriceRepository priceRepository;

  @Test
  void fetchAllUsersEndpoint() throws Exception{
    final MvcResult result = this.mockMvc.perform(get("/api/v1/all")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn(); // Obtiene el resultado para su posterior análisis

    final String contentAsString = result.getResponse().getContentAsString();
    final ObjectMapper objectMapper = new ObjectMapper();
    final List<Object> responseList = objectMapper.readValue(contentAsString, new TypeReference<>() {});
    assertThat(this.priceRepository.findAll()).hasSize(responseList.size());
  }

  @Test
  void getApplicableRanges() throws Exception {
    final CreatePriceRequest priceRequest=new CreatePriceRequest(LocalDateTime.parse("2020-06-14T10:00:00"),35455,"1");
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    this.mockMvc.perform(post("/api/v1/ranges")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(priceRequest))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].productId" , is(35455)))
            .andExpect(jsonPath("$[0].startDate", is("2020-06-14T00:00:00")))
            .andExpect(jsonPath("$[0].endDate", is("2020-12-31T23:59:59")))
            .andExpect(jsonPath("$[0].chainIdentifier" , is(1)))
            .andExpect(jsonPath("$[0].rateToApply" , is(1)))
            .andExpect(jsonPath("$[0].finalPrice" , is(35.50)));
  }

  @ParameterizedTest
  @CsvSource({
          "2020-06-14T10:00:00,35455,1,35455,2020-06-14T00:00:00,2020-12-31T23:59:59,1,1,35.50", // Prueba 1: realizar una petición a las 10:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
          "2020-06-14T16:00:00,35455,1,35455,2020-06-14T15:00:00,2020-06-14T18:30:00,1,2,25.45", // Prueba 2: realizar una petición a las 16:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
          "2020-06-14T21:00:00,35455,1,35455,2020-06-14T00:00:00,2020-12-31T23:59:59,1,1,35.50", // Prueba 3: realizar una petición a las 21:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
          "2020-06-15T10:00:00,35455,1,35455,2020-06-15T00:00:00,2020-06-15T11:00:00,1,3,30.50", // Prueba 4: realizar una petición a las 10:00 del día 15 para el producto 35455 y la marca 1 (ZARA).
          "2020-06-16T21:00:00,35455,1,35455,2020-06-15T16:00:00,2020-12-31T23:59:59,1,4,38.95"  // Prueba 5: realizar una petición a las 21:00 del día 16 para el producto 35455 y la marca 1 (ZARA).
  })
  void testScenarios(@AggregateWith(PriceRequestAggregator.class) CreatePriceRequest createPriceRequest,
                     @AggregateWith(ApplicableRangeAggregator.class) ApplicableRangeDTO applicableRangeDTO) throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    this.mockMvc.perform(post("/api/v1/ranges")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(createPriceRequest))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].productId" , is(applicableRangeDTO.getProductId())))
            .andExpect(jsonPath("$[0].startDate", is(applicableRangeDTO.getStartDate().format(formatter))))
            .andExpect(jsonPath("$[0].endDate", is(applicableRangeDTO.getEndDate().format(formatter))))
            .andExpect(jsonPath("$[0].chainIdentifier" , is(applicableRangeDTO.getBrandId())))
            .andExpect(jsonPath("$[0].rateToApply" , is(applicableRangeDTO.getPriceList())))
            .andExpect(jsonPath("$[0].finalPrice" , is(applicableRangeDTO.getPrice().doubleValue())));
  }

  @Test
  void shouldReturnBadRequestWhenBrandIdIsInvalid() throws Exception {
    // Arrange: Create a request with an invalid BrandId
    final CreatePriceRequest invalidRequest = new CreatePriceRequest(
            LocalDateTime.parse("2020-06-14T10:00:00"),
            35455,
            "invalidBrandId"
    );
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    // Act & Assert: Perform the POST request and expect a 400 Bad Request
    this.mockMvc.perform(post("/api/v1/ranges")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(invalidRequest))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("BrandId does not contain a valid number"));
  }
}
