package model.converters.others;

import exceptions.AppException;
import model.car.Car;
import model.converters.json.CarConverter;
import model.validator.CarValidator;


public class CarConverterWithValidation {

  public Car toCar(final String jsonFilename) {

    if (jsonFilename == null || !jsonFilename.matches(".+\\.json")) {
      throw new AppException("INPUT FILE FORMAT IS NOT ACCEPTED!");
    }

    CarValidator carValidator = new CarValidator();

    Car car = new CarConverter(jsonFilename)
            .fromJson()
            .orElseThrow(() -> new AppException("FILE " + jsonFilename + " is empty"));

    carValidator.validate(car);

    if (!carValidator.hasErrors()) {
      return car;
    } else {
      throw new AppException("CAR IS NOT VALID");
    }

  }

}
