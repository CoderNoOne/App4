package model.car;

import lombok.Data;

import java.util.List;

@Data
public class CarBody {
  private CarBodyColor color;
  private CarBodyType type;
  private List<String> components;


  @Override
  public String toString() {
    return "CarBody{" +
            "color=" + color +
            ", type=" + type +
            ", components=" + components +
            '}';
  }
}
