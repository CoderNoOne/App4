package model.statistics;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class BigDecimalCollector implements Collector<BigDecimal, BigDecimalCollector.PriceSummary, BigDecimalCollector.PriceSummary> {

  @Data
  public static class PriceSummary {

    private BigDecimal sum;
    private int count;
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getAverage() {
      BigDecimal bigCount = new BigDecimal(count);
      return BigDecimal.ZERO.compareTo(bigCount) == 0 ? BigDecimal.ZERO : sum.divide(bigCount, 2, RoundingMode.HALF_UP);
    }
  }

  @Override
  public Supplier<PriceSummary> supplier() {
    return PriceSummary::new;
  }

  @Override
  public BiConsumer<PriceSummary, BigDecimal> accumulator() {
    return (a, p) -> {
      a.setSum(a.getSum() == null ? p: a.getSum().add(p));
      a.count += 1;
      a.setMin(a.getMin() == null ? p : a.getMin().compareTo(p) <= 0 ? a.getMin() : p);
      a.setMax(a.getMax() == null ? p : a.getMax().compareTo(p) >= 0 ? a.getMax() : p);
    };
  }

  @Override
  public BinaryOperator<PriceSummary> combiner() {
    return (a, b) -> {
      PriceSummary s = new PriceSummary();
      s.sum = a.sum.add(b.sum);
      s.count = a.count + b.count;
      s.max = a.getMax().compareTo(b.getMax()) >= 0 ? a.getMax() : b.getMax();
      s.min = a.getMin().compareTo(b.getMin()) >= 0 ? a.getMin() : b.getMin();

      return s;
    };
  }

  @Override
  public Function<PriceSummary, PriceSummary> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }

}
