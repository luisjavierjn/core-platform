package com.inditex.coreplatform.utils;

import com.inditex.coreplatform.domain.requests.CreatePriceRequest;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.time.LocalDateTime;

public class PriceRequestAggregator implements ArgumentsAggregator {
  @Override
  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
    return new CreatePriceRequest(
            LocalDateTime.parse(accessor.getString(0)),
            accessor.getInteger(1),
            accessor.getString(2));
  }
}
