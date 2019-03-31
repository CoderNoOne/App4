package model.sorting;


import exceptions.AppException;
import model.car.Car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MySort {

  private List<Comparator<Car>> comparators;

  private MySort(MySortBuilder mySortBuilder) {
    comparators = mySortBuilder.comparators;
  }

  public Comparator<Car> getComparator() {

    if (comparators.isEmpty()) {
      throw new AppException("NO COMPARATORS AVAILABLE");
    }
    Comparator<Car> comparator = comparators.get(0);

    comparators.stream().skip(1).forEach(comparator::thenComparing);

    return comparator;
  }

  public static class MySortBuilder {

    private List<Comparator<Car>> comparators = new ArrayList<>();

    public MySortBuilder enginePower(boolean isAscendingOrder) {
      Function<Car, BigDecimal> enginePower = car -> car.getEngine().getPower();
      comparators.add(isAscendingOrder ? Comparator.comparing(enginePower) : Comparator.comparing(enginePower).reversed());
      return this;
    }

    public MySortBuilder tyreSize(boolean isAscendingOrder) {
      Function<Car, Integer> wheelSize = car -> car.getWheel().getSize();
      comparators.add(isAscendingOrder ? Comparator.comparing(wheelSize) : Comparator.comparing(wheelSize).reversed());
      return this;
    }

    public MySortBuilder noOfComponents(boolean isAscendingOrder) {

      Function<Car, Integer> noOfComponents = car -> car.getCarBody().getComponents().size();
      comparators.add(isAscendingOrder ? Comparator.comparing(noOfComponents) : Comparator.comparing(noOfComponents).reversed());
      return this;
    }

    public MySort build() {
      return new MySort(this);
    }
  }
}
