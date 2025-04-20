package com.inditex.coreplatform.utils;

import com.inditex.coreplatform.domain.dto.ApplicableRangeDTO;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApplicableRangeAggregator implements ArgumentsAggregator {
  @Override
  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
    return ApplicableRangeDTO.builder()
            .productId(accessor.getInteger(3))
            .startDate(LocalDateTime.parse(accessor.getString(4)))
            .endDate(LocalDateTime.parse(accessor.getString(5)))
            .brandId(accessor.getInteger(6))
            .priceList(accessor.getInteger(7))
            .price(new BigDecimal(accessor.getString(8)))
            .build();
  }
}
