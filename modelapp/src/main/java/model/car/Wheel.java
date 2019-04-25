package model.car;

public class Wheel {
  private String model;
  private int size;
  private TyreType type;

  public Wheel() {
  }

  public String getModel() {
    return model;
  }
  public int getSize() {
    return size;
  }
  public TyreType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Wheel{" +
            "model='" + model + '\'' +
            ", size=" + size +
            ", type=" + type +
            '}';
  }
}
