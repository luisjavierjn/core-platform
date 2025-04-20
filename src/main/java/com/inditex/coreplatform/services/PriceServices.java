package com.inditex.coreplatform.services;

import com.inditex.coreplatform.domain.dto.ApplicableRangeDTO;
import com.inditex.coreplatform.domain.dto.PriceDTO;
import com.inditex.coreplatform.domain.requests.CreatePriceRequest;
import com.inditex.coreplatform.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServices {
  private final PriceRepository priceRepository;
  private final ModelMapper modelMapper;

  public List<PriceDTO> getAllPrices() {
    return this.priceRepository.findAll()
            .stream()
            .map(price -> this.modelMapper.map(price, PriceDTO.class))
            .collect(Collectors.toList());
  }

  public List<ApplicableRangeDTO> getApplicableRanges(CreatePriceRequest request) {
    return this.priceRepository.findApplicableRanges(request.getApplicationDate(),
            request.getProductId(),
            Integer.parseInt(request.getBrandId()));
  }
}
