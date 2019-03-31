package model.car;

import exceptions.AppException;
import lombok.Getter;
import lombok.ToString;
import model.converters.json.CarConverter;

import java.math.BigDecimal;

@Getter
@ToString
public class Car {

  private String model;
  private BigDecimal price;
  private int mileage;
  private CarBody carBody;
  private Wheel wheel;
  private Engine engine;

  public Car(final String jsonFilename) {

    readCarFromJsonFile(jsonFilename);

  }

  private static Car readCarFromJsonFile(final String jsonFilename) {

    if (jsonFilename == null || !jsonFilename.matches("[\\w]+.json")) {
      throw new AppException("WRONG JSON FILE");
    }

    return new CarConverter(jsonFilename)
            .fromJson()
            .orElseThrow(() -> new AppException("File " + jsonFilename + " is empty"));
  }


}


