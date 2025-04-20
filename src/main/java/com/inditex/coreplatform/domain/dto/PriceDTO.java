package com.inditex.coreplatform.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PriceDTO {
  private Integer id;
  private Integer brandId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Integer priceList;
  private Integer productId;
  private Integer priority;
  private BigDecimal price;
  private String curr;
}
