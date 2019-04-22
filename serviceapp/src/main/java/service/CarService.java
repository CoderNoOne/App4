package service;

import converters.others.CarsConverter;
import exceptions.AppException;

import model.car.Car;
import model.car.CarBodyType;
import model.car.EngineType;
import model.car.TyreType;
import model.sorting.MySort;
import model.statistics.BigDecimalCollector;
import model.statistics.Quantity;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CarService {

  private Set<Car> cars;

  public CarService(final String... jsonFilenames) {
    cars = readCarsFromJsonFile(jsonFilenames);
  }

  private static Set<Car> readCarsFromJsonFile(final String... jsonFilename) {

    if (jsonFilename == null || jsonFilename.length == 0) {
      throw new AppException("YOU DIDN'T SPECIFY ANY FILE");
    }

    return new CarsConverter()
            .toCarSet(jsonFilename);

  }

  public Map<TyreType, List<Car>> carsWithRespectToTyreType() {

    return cars.stream()
            .collect(Collectors.collectingAndThen(Collectors.groupingBy(car -> car.getWheel().getType()),
                    map -> map.entrySet().stream().sorted((e1, e2) ->
                            Integer.compare(e2.getValue().size(), e1.getValue().size()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (i1, i2) -> i1, LinkedHashMap::new))));
  }

  public List<Car> carListWithSpecifiedComponents(List<String> components) {

    if (components.isEmpty()) throw new AppException("YOU DEFINE NO COMPONENT!");
    return cars.stream()
            .filter(car -> car.getCarBody().getComponents().containsAll(components))
            .sorted((Comparator.comparing(Car::getModel)))
            .collect(Collectors.toList());

  }

  public Map<Car, Integer> mileagesByCar() {

    return cars.stream()
            .sorted(Comparator.comparingInt(Car::getMileage).reversed())
            .collect(Collectors.toMap(
                    Function.identity(),
                    Car::getMileage,
                    (i1, i2) -> i1,
                    LinkedHashMap::new)
            );
  }

  public Set<String> carModels(EngineType engineType) {

    return cars.stream()
            .filter(car -> car.getEngine().getType() == engineType)
            .map(Car::getModel)
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));

  }

  public List<Car> carListWithSpecifiedCarBodyType(CarBodyType carBodyType, BigDecimal minPrice, BigDecimal maxPrice) {

    return cars.stream()
            .filter(car -> car.getCarBody().getType() == carBodyType)
            .filter(car -> car.getPrice().compareTo(minPrice) >= 0 && car.getPrice().compareTo(maxPrice) <= 0)
            .collect(Collectors.toList());
  }

  public List<Car> sortedCarListBySelectedCriterion(MySort mySort) {

    return cars.stream()
            .sorted(mySort.getComparator())
            .collect(Collectors.toList());
  }

  public Map<String, BigDecimal> statisticsForSpecifiedQuantity(Quantity quantity) {

    switch (quantity) {
      case MILEAGE:
        return statistics(car -> new BigDecimal(car.getMileage()));
      case PRICE:
        return statistics(Car::getPrice);
      case ENGINE_POWER:
        return statistics(car -> car.getEngine().getPower());
      default:
        throw new AppException("STATISTICS FOR THAT QUANITY IS NOT IMPLEMENTED");
    }

  }

  private Map<String, BigDecimal> statistics(Function<Car, BigDecimal> function) {

    return cars.stream()
            .map(function)
            .collect(Collectors.collectingAndThen(new BigDecimalCollector(),
                    colector -> Map.of("AVERAGE: ", colector.getAverage(),
                            "MAX: ", colector.getMax(),
                            "MIN: ", colector.getMin())));
  }

  public Set<Car> getCars() {
    return cars;
  }
}
