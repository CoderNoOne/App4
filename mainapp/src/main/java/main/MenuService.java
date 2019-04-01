package main;


import exceptions.AppException;
import model.car.CarBodyType;
import model.car.EngineType;
import model.sorting.MySort;
import model.statistics.Quantity;
import service.CarService;
import service.UserDataService;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MenuService {

  private final CarService carService;
  private final UserDataService userDataService = new UserDataService();


  public MenuService(final String... jsonFilenames) {

    carService = new CarService(jsonFilenames);
  }

  public void mainMenu() {
    menuOptions();
    while (true)
      try {
        int option = userDataService.getInt("MAIN MENU: INPUT YOUR OPTION: ");
        switch (option) {
          case 1:
            option1();
            break;
          case 2:
            option2();
            break;
          case 3:
            option3();
            break;
          case 4:
            option4();
            break;
          case 5:
            option5();
            break;
          case 6:
            option6();
            break;
          case 7:
            option7();
            break;
          case 8:
            menuOptions();
            break;
          case 9:
            userDataService.close();
            return;
          default:
            throw new AppException("INPUT OPTION IS NOT DEFINED");
        }
      } catch (AppException e) {
        System.out.println(e.getExceptionMessage());
        System.err.println(Arrays.toString(e.getStackTrace()));
      }
  }


  private static void menuOptions() {

    String menu = MessageFormat.format(
            "\nOption no. 1 - {0}\n" +
                    "Option no. 2 - {1}\n" +
                    "Option no. 3 - {2}\n" +
                    "Option no. 4 - {3}\n" +
                    "Option no. 5 - {4}\n" +
                    "Option no. 6 - {5}\n" +
                    "Option no. 7 - {6}\n" +
                    "Option no. 8 - {7}\n" +
                    "Option no. 9 - {8}\n",

            "List of carService grouped by TyreType",
            " List of carService with prize within range and with specified Car Body Type",
            "LIST OF CARS WITH SPECIFIED COMPONENTS",
            "MILEAGE BY CAR",
            "DISTINCT CAR MODELS WITH SPECIFIED ENGINE TYPE",
            "SORT CARS BY SPECIFIED CRITERIUM AND WITH SPECIFIED ORDER",
            "STATISTICS FOR SPECIFIED QUANTITY",
            "MENU OPTIONS",
            "EXIT"

    );

    System.out.println(menu);

  }

  private void option7() {

    Map<Integer, Quantity> quantityMap = new LinkedHashMap<>();

    Arrays.stream(Quantity.values()).forEach(quantity -> quantityMap.put(quantity.ordinal(), quantity));

    quantityMap.forEach((k, v) -> System.out.println("QUANTITY NO. " + ++k + " " + v));

    int choice = userDataService.getInt("ENTER QUANTITY FROM ABOVE: ");
    Quantity quantity;

    switch (choice) {
      case 1:
        quantity = quantityMap.get(0);
        break;
      case 2:
        quantity = quantityMap.get(1);
        break;
      case 3:
        quantity = quantityMap.get(2);
        break;
      default:
        throw new AppException("ENTERED QUANTITY NOT ALLOWED");
    }

    carService.statisticsForSpecifiedQuantity(quantity).forEach((k, v) -> System.out.println(k + v));
  }

  private void option6() {

    MySort sortingAlgorithm = userDataService.getSortingAlgorithm("INPUT YOUR SORTING ALGORITHMS");
    System.out.println(carService.sortedCarListBySelectedCriterium(sortingAlgorithm));

  }

  private void option5() {

    int[] arr = {0};
    Set<EngineType> engineTypes = carService.getCars().stream()
            .map(car -> car.getEngine().getType())
            .collect(Collectors.toSet());

    engineTypes.forEach(type -> System.out.println("ENGINE TYPE no. " + arr[0]++ + " " + type));
    String engineType = userDataService.getString("ENTER ENGINE TYPE FROM ABOVE: ").toUpperCase();

    if (engineTypes.stream().anyMatch(enT -> enT.toString().equals(engineType))) {
      System.out.println(carService.carModels(EngineType.valueOf(engineType)));
    } else {
      throw new AppException("BAD ENGINE TYPE");
    }

  }

  private void option4() {
    carService.mileagesByCar().forEach((k, v) -> System.out.println("Car: " + k + " -> Mileage: " + v));
  }

  private void option3() {

    List<String> list = new ArrayList<>();

    Set<String> components = carService.getCars().stream()
            .flatMap(car -> car.getCarBody().getComponents().stream())
            .collect(Collectors.toSet());

    et:
    while (true) {
      option3Menu();
      int option = userDataService.getInt("Choose option");
      switch (option) {
        case 1:
          if (components.size() > list.size()) {
            int[] arr = {1};

            components.stream()
                    .filter(component -> !list.contains(component))
                    .forEach(component -> System.out.println("Component " + arr[0]++ + ": " + component));

            boolean isValid;
            do {

              String userInput = userDataService.getString(" INPUT COMPONENT: ");
              isValid = components.stream().anyMatch(c -> c.equalsIgnoreCase(userInput));
              if (isValid) list.add(userInput);
              else System.out.println("YOUR INPUT DOESN'T MATCH ANY COMPONENT. INPUT PROPER COMPONENT NAME");
            } while (!isValid);
          } else {
            System.out.println("YOU HAVE CHOOSEN ALL THE COMPONENTS ARLEADY");
            break et;
          }
          break;
        case 2:
          break et;
        default:
          throw new AppException("OPTION IS NOT AVAILABLE! ");
      }
    }

    System.out.println(carService.carListWithSpecifiedComponents(list));
  }

  private static void option3Menu() {
    System.out.println(MessageFormat.format("Option no. 1: {0}\nOption no. 2: {1}", "ADD COMPONENT", "EXIT"));
  }

  private void option2() {

    BigDecimal minPrice = userDataService.getBigDecimal("INPUT MIN PRICE: ");

    BigDecimal maxPrice = userDataService.getBigDecimal("INPUT MAX PRICE: ");

    if (minPrice.compareTo(maxPrice) > 0) throw new AppException("MIN PRICE CANNOT BE GREATER THAN MAX PRICE");

    int[] arr = {1};
    Arrays.stream(CarBodyType.values()).forEach(x -> System.out.println(arr[0]++ + ": " + x));
    String carBody = userDataService.getString("SELECT CAR BODY TYPE FROM ABOVE").toUpperCase();

    if (Arrays.stream(CarBodyType.values()).noneMatch(x -> x.toString().equals(carBody))) {
      throw new AppException("ENTERED CAR BODY TYPE IS NOT ALLOWED");
    }

    System.out.println(carService.carListWithSpecifiedCarBodyType(CarBodyType.valueOf(carBody),
            minPrice, maxPrice));
  }

  private void option1() {
    carService.carsWithRespectToTyreType().forEach((k, v) -> System.out.println("Tyre: " + k + " List: " + v));
  }

}