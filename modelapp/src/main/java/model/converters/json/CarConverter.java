package model.converters.json;

import model.car.Car;

public class CarConverter extends JsonConverter<Car> {

  public CarConverter(String jsonFilename) {
    super(jsonFilename);
  }
}
