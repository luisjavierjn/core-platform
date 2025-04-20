package com.inditex.coreplatform.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ApplicableRangeDTO {

  public ApplicableRangeDTO(Integer productId, Integer brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, Integer priority) {
    this.productId = productId;
    this.brandId = brandId;
    this.priceList = priceList;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
    this.priority = priority;
  }

  private Integer productId;
  @JsonProperty("chainIdentifier")
  private Integer brandId;
  @JsonProperty("rateToApply")
  private Integer priceList;
  @Setter
  private LocalDateTime startDate;
  @Setter
  private LocalDateTime endDate;
  @JsonProperty("finalPrice")
  private BigDecimal price;
  @JsonIgnore
  private Integer priority;
}
