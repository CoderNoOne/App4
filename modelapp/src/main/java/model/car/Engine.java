package model.car;



import java.math.BigDecimal;


public class Engine {

  private EngineType type;
  private BigDecimal power;


  public EngineType getType() {
    return type;
  }

  public BigDecimal getPower() {
    return power;
  }

  @Override
  public String toString() {
    return "Engine{" +
            "type=" + type +
            ", power=" + power +
            '}';
  }
}
