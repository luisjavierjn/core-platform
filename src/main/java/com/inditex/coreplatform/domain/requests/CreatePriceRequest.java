package com.inditex.coreplatform.domain.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreatePriceRequest {
  private LocalDateTime applicationDate;
  private int productId;
  @NotEmpty(message = "Brand Id should not be empty")
  private String brandId;
}
