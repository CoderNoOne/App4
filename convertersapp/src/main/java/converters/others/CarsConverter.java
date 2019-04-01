package converters.others;

import model.car.Car;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CarsConverter {

  public Set<Car> toCarsSet(final String... jsonFilenames) {

    var carConverterWithValidation = new CarConverterWithValidation();

    return Arrays.stream(jsonFilenames).map(carConverterWithValidation::toCar).collect(Collectors.toSet());

  }
}
