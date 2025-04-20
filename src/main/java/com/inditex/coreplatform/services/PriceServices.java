package com.inditex.coreplatform.services;

import com.inditex.coreplatform.domain.dto.PriceDTO;
import com.inditex.coreplatform.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServices {
  private final PriceRepository priceRepository;

  public List<PriceDTO> getAllPrices() {
    return null;
  }
}
