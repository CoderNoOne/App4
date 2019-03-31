package service;


import exceptions.AppException;
import model.sorting.MySort;
import model.sorting.SortingCriterium;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserDataService {

  private Scanner sc = new Scanner(System.in);

  public int getInt(String message) {
    System.out.println(message);

    String text = sc.nextLine();
    if (!text.matches("[\\d]+")) {
      throw new AppException("INT VALUE IS NOT CORRECT: " + text);
    }

    return Integer.parseInt(text);
  }


  public String getString(String inputMessage) {

    System.out.println(inputMessage);

    String input = sc.nextLine();

    if (input.length() == 0) {
      throw new AppException("YOU DIDN'T INPUT ANY VALUE");
    }

    return input;
  }


  public MySort getSortingAlgorithm(String message) {

    List<String> sortingAlgorithms = Arrays.stream(SortingCriterium.values()).map(Enum::toString).collect(Collectors.toList());
    System.out.println(message);
    MySort.MySortBuilder builder = new MySort.MySortBuilder();

    while (true) {

      System.out.println("CHOOSE FROM ABOVE: " + sortingAlgorithms);
      String input = sc.nextLine().toUpperCase();

      if (!sortingAlgorithms.contains(input)) throw new AppException("BAD SORTING CRITERIUM");

      switch (input) {
        case "ENGINE_POWER":
          String input1 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          sortingAlgorithms.remove("MODEL");
          builder = input1.equals("ASC") ? builder.enginePower(true) : builder.enginePower(false);
          break;
        case "TYRE_SIZE":
          String input2 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          builder = input2.equals("ASC") ? builder.tyreSize(true) : builder.tyreSize(false);
          sortingAlgorithms.remove("PRICE");
          break;
        case "NO_OF_COMPONENTS":
          String input3 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          builder = input3.equals("ASC") ? builder.noOfComponents(true) : builder.noOfComponents(false);
          sortingAlgorithms.remove("MILEAGE");
          break;
      }

      String input4 = getString("DO YOU WANT TO ADD NEW SORTING CRITERIUM? Y/N");
      if (!input4.equalsIgnoreCase("y")) break;
    }
    return builder.build();
  }


  public BigDecimal getBigDecimal(String message) {

    System.out.println(message);

    String input = sc.nextLine();

    if (!input.matches("[\\d]+(\\.\\d+)?")) {
      throw new AppException("BAD FORMAT");
    }

    return new BigDecimal(input);
  }


  public void close() {
    if (sc != null) {
      sc.close();
      sc = null;
    }
  }
}