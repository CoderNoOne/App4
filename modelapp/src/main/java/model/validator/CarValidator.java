package model.validator;

import model.car.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {

  private Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(Car car) {

    errors.clear();

    if (car == null) {
      errors.put("car", "car object is null");
      return errors;
    }

    if (!areWheelValid(car)) {
      errors.put("wheel", "wheel size is not valid");
    }

    if (!areComponentsValid(car)) {
      errors.put("components", "components are not valid");
    }

    if (!isModelValid(car)) {
      errors.put("model", "car model is not valid");
    }

    if (!isMileageValid(car)) {
      errors.put("mileage", "car mileage is not valid");
    }

    if (!isPriceValid(car)) {

      errors.put("price", "car price is not valid");
    }
    if (!isEngineValid(car)) {

      errors.put("Engine power", "Car engine  power is not valid");
    }

    if (!isTyreTypeValid(car)) {
      errors.put("TyreType", "tyre type is not valid");
    }

    if (!isCarBodyColorValid(car)) {
      errors.put("Car body color", "car body color is not valid");
    }

    if (!isCarBodyTypeValid(car)) {

      errors.put("Car Body type", "Car body type is not valid");
    }

    if (!isEngineTypeValid(car)) {

      errors.put("Car engine type", "Car engine type is not valid");
    }


    return errors;
  }

  private boolean isEngineValid(Car car) {

    return car.getEngine().getPower().compareTo(BigDecimal.ZERO) > 0
            && car.getEngine().getPower().toString().matches("\\d*\\.\\d");
  }


  private boolean areWheelValid(Car car) {

    return car.getWheel().getSize() > 0;
  }

  private boolean areComponentsValid(Car car) {

    return car.getCarBody().getComponents().stream()
            .flatMap(element -> element.chars().mapToObj(el -> (char) el))
            .allMatch(CarValidator::testCharacter);
  }


  private boolean isModelValid(Car car) {

    return car.getModel().chars().mapToObj(el -> (char) el)
            .allMatch(CarValidator::testCharacter);

  }

  private boolean isMileageValid(Car car) {
    return car.getMileage() > 0;
  }

  private boolean isPriceValid(Car car) {

    return car.getPrice().compareTo(BigDecimal.ZERO) > 0;

  }

  private boolean isTyreTypeValid(Car car) {

    return Arrays.stream(TyreType.values()).anyMatch(type -> type.equals(car.getWheel().getType()));

  }

  private boolean isCarBodyColorValid(Car car) {

    return Arrays.stream(CarBodyColor.values()).anyMatch(color -> color.equals(car.getCarBody().getColor()));
  }

  private boolean isCarBodyTypeValid(Car car) {

    return Arrays.stream(CarBodyType.values()).anyMatch(bodyType -> bodyType.equals(car.getCarBody().getType()));

  }

  private boolean isEngineTypeValid(Car car) {

    return Arrays.stream(EngineType.values()).anyMatch(engineType -> engineType.equals(car.getEngine().getType()));
  }

  private static boolean testCharacter(Character character) {
    return Character.isUpperCase(character) || Character.isWhitespace(character);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

}
