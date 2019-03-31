package model.car;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Engine {

  private EngineType type;
  private BigDecimal power;

  @Override
  public String toString() {
    return "Engine{" +
            "type=" + type +
            ", power=" + power +
            '}';
  }
}
