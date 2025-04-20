package com.inditex.coreplatform.controllers;

import com.inditex.coreplatform.domain.dto.PriceDTO;
import com.inditex.coreplatform.domain.requests.CreatePriceRequest;
import com.inditex.coreplatform.services.PriceServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PriceController {
  private final PriceServices priceServices;

  @GetMapping("/all")
  public ResponseEntity<List<PriceDTO>> getAllPrices() {
    return ResponseEntity.ok(this.priceServices.getAllPrices());
  }

  @PostMapping("/ranges")
  public ResponseEntity<?> getApplicableRanges(@RequestBody @Valid CreatePriceRequest request) {
    try {
      Integer.parseInt(request.getBrandId());
      return ResponseEntity.ok(this.priceServices.getApplicableRanges(request));
    } catch (final NumberFormatException e) {
      return ResponseEntity.badRequest().body("BrandId does not contain a valid number");
    }
  }
}
