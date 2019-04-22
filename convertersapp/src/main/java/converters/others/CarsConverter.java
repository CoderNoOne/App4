package converters.others;

import converters.json.CarConverter;
import exceptions.AppException;
import model.car.Car;
import validator.CarValidator;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CarsConverter {

  public Set<Car> toCarSet(final String... jsonFilenames) {

    var carValidator = new CarValidator();
    AtomicInteger atomicInteger = new AtomicInteger(1);

    return Arrays.stream(jsonFilenames).map(CarConverter::new)
            .map(carConverter -> carConverter
                    .fromJson()
                    .orElseThrow(() -> new AppException("File " + carConverter.getJsonFilename() + " is empty")))
            .filter(car -> {

              Map<String, String> carErrors = carValidator.validate(car);

              if (carValidator.hasErrors()) {
                System.out.println("CAR NO: " + atomicInteger.get());
                System.out.println(carErrors.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()).collect(Collectors.joining("\n")));

              }
              atomicInteger.incrementAndGet();
              return !carValidator.hasErrors();
            }).collect(Collectors.toSet());
  }
}
