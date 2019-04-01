package model.car;




public class Wheel {
  private String model;
  private int size;
  private TyreType type;


  public String getModel() {
    return model;
  }

  public int getSize() {
    return size;
  }

  public TyreType getType() {
    return type;
  }

  public Wheel(String model, int size, TyreType type) {
    this.model = model;
    this.size = size;
    this.type = type;
  }

  public Wheel() {
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
