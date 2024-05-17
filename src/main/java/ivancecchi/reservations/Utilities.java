package ivancecchi.reservations;

import ivancecchi.reservations.entities.HasId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class Utilities {

    private static Scanner scanner;

    @Autowired
    public Utilities(Scanner scanner) {
        Utilities.scanner = scanner;
    }

    public static <T extends HasId> T askAndVerifyList(String question, List<T> list, String elementName, boolean showList) {
        if (elementName == null) {
            elementName = "Element";
        }
        if (list.isEmpty()) {
            System.err.println("No " + elementName + " present");
            return null;
        }
        if (showList) {
            list.forEach(System.out::println);
        }
        T element = null;
        while (true) {
            System.out.println(question);

            try {
                String idElement = scanner.nextLine();
                element = list.stream().filter(e -> Objects.equals(e.getId().toString(), idElement)).findFirst().orElse(null);
                if (element == null) {
                    System.err.println(elementName + " not found");
                } else {
                    return element;
                }
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }

    public static String askAndVerifyStringList(String question, List<String> list) {

        if (list.isEmpty()) {
            System.err.println("No string present");
            return null;
        }
        String element = null;
        while (true) {
            System.out.println(question);
            list.forEach(System.out::println);

            try {
                String s = scanner.nextLine();
                element = list.stream().filter(e -> Objects.equals(e, s)).findFirst().orElse(null);
                if (element == null) {
                    System.err.println("String not found");
                } else {
                    return element;
                }
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }

    public static <T extends Enum<T>> T askAndVerifyEnum(String question, Class<T> enumClass) {
        while (true) {
            System.out.println(question);
            List<Integer> integers = new ArrayList<>();
            int i = 1;
            for (T t : enumClass.getEnumConstants()) {
                integers.add(i);
                System.out.println(i + ". " + t.name());
                i++;
            }
            try {
                int output = Integer.parseInt(scanner.nextLine());
                if (integers.contains(output)) {
                    return enumClass.getEnumConstants()[output - 1];
                } else {
                    System.err.println("Insert a valid number");
                }
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }

    public static <T extends Enum<T>> T askAndVerifyEnum(String question, Class<T> enumClass, int lastEmentsToRemove) {
        while (true) {
            System.out.println(question);
            List<Integer> integers = new ArrayList<>();

            T[] enumElements = enumClass.getEnumConstants();
            for (int j = 0; j < enumElements.length - lastEmentsToRemove; j++) {
                System.out.println(j + 1 + ". " + enumElements[j].name());
                integers.add(j + 1);
            }
            try {
                int output = Integer.parseInt(scanner.nextLine());
                if (integers.contains(output)) {
                    return enumClass.getEnumConstants()[output - 1];
                } else {
                    System.err.println("Insert a valid number");
                }
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }


    public static int askAndVerifyInt(String question) {
        while (true) {
            System.out.println(question);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }

    public static int askAndVerifyIntInList(String question, List<Integer> list) {
        while (true) {
            System.out.println(question);
            try {
                int output = Integer.parseInt(scanner.nextLine());
                if (list.contains(output)) {
                    return output;
                } else {
                    System.err.println("Insert a valid number");
                }
            } catch (NumberFormatException e) {
                System.err.println("Insert a valid number");
            }
        }
    }

    public static String askAndVerifyString(String question) {
        while (true) {
            System.out.println(question);

            String string = scanner.nextLine();

            if (string.isEmpty()) {
                System.err.println("Insert a valid value");
            } else {
                return string;
            }
        }
    }

    public static String askAndVerifyString(String question, int length) {
        while (true) {
            System.out.println(question);

            String string = scanner.nextLine();

            if (string.isEmpty() || string.length() != length) {
                System.err.println("Insert a valid value");
            } else {
                return string;
            }
        }
    }

    public static LocalDate askAndVerifyDate(String question) {
        while (true) {
            System.out.println(question);

            String string = scanner.nextLine();

            if (string.isEmpty()) {
                System.err.println("Insert a valid date");
            } else {
                try {
                    return LocalDate.parse(string);
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid date format, try again");
                    System.out.println();
                }
            }
        }
    }

    public static void pressEnterToContinue() {
        System.out.println();
        System.out.println("Press enter to continue");
        scanner.reset();
        scanner.nextLine();
        scanner.reset();

    }
}
