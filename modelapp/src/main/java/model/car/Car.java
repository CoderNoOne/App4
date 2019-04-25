package model.car;

import java.math.BigDecimal;

public class Car {

  private String model;
  private BigDecimal price;
  private int mileage;
  private CarBody carBody;
  private Wheel wheel;
  private Engine engine;

  public Car() {}

  public String getModel() {
    return model;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getMileage() {
    return mileage;
  }

  public CarBody getCarBody() {
    return carBody;
  }

  public Wheel getWheel() {
    return wheel;
  }

  public Engine getEngine() {
    return engine;
  }

  @Override
  public String toString() {
    return "Car{" +
            "model='" + model + '\'' +
            ", price=" + price +
            ", mileage=" + mileage +
            ", carBody=" + carBody +
            ", wheel=" + wheel +
            ", engine=" + engine +
            '}';
  }
}


