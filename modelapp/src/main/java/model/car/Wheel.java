package model.car;

import lombok.Data;

@Data
public class Wheel {
  private String model;
  private int size;
  private TyreType type;

}
