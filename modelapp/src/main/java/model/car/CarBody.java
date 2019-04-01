package model.car;



import java.util.List;


public class CarBody {
  private CarBodyColor color;
  private CarBodyType type;
  private List<String> components;


  public CarBodyColor getColor() {
    return color;
  }

  public CarBodyType getType() {
    return type;
  }

  public List<String> getComponents() {
    return components;
  }


  public CarBody(CarBodyColor color, CarBodyType type, List<String> components) {
    this.color = color;
    this.type = type;
    this.components = components;
  }

  public CarBody() {
  }

  @Override
  public String toString() {
    return "CarBody{" +
            "color=" + color +
            ", type=" + type +
            ", components=" + components +
            '}';
  }
}
