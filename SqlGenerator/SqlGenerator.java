import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.lang.NumberFormatException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

public class SqlGenerator {
  public static void main (String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    do {
      displayMainMenu();
      try {
        int input = Integer.parseInt(scanner.nextLine());
        System.out.println();
        switch (input) {
          case 1:
            chooseInsertTables(scanner);
            break;
          case 2:
            break;
          case 3:
            exit = true;
            break;
          default:
            System.err.println("Please enter a valid option");
            System.out.println();
            break;
        }
      } catch (NumberFormatException e) {
        System.err.println("Please enter a valid input");
        System.out.println();
      }
    } while (!exit);
  }

  private static void displayMainMenu() {
    System.out.println("============= SQL Generator =============");
    System.out.println("1. Create INSERT statement");
    System.out.println("2. Create UPDATE statement");
    System.out.println("3. Exit");
    System.out.print("Select an option > ");
  }

  private static void chooseInsertTables(Scanner scanner) {
    boolean exit = false;
    List<String> outputSql = new ArrayList<String>();

    do {
      displayInsertTableSelectionMenu();
      try {
        boolean hasFileInputTable = false;
        Pair columnData;
        Pair valueData;

        int input = Integer.parseInt(scanner.nextLine());
        System.out.println();
        switch (input) {
          case 1:
            System.out.print("Type the table name: ");
            String tableName = scanner.nextLine();
            System.out.println();
            columnData = chooseInsertColumns(scanner);
            valueData = chooseInsertValues(scanner);
            outputSql = buildInsertSql(hasFileInputTable, tableName, columnData, valueData);
            writeToFile(outputSql);
            return;
          case 2:
            hasFileInputTable = true;
            System.out.print("Type the file name with file extension (file must be in root folder): ");
            String fileName = scanner.nextLine();
            System.out.println();
            columnData = chooseInsertColumns(scanner);
            valueData = chooseInsertValues(scanner);
            outputSql = buildInsertSql(hasFileInputTable, fileName, columnData, valueData);
            writeToFile(outputSql);
            return;
          case 3:
            exit = true;
            break;
          default:
            System.err.println("Please enter a valid option");
            System.out.println();
            break;
        }
      } catch (NumberFormatException e) {
        System.err.println("Please enter a valid input");
        System.out.println();
      }
    } while (!exit);
  }

  private static void displayInsertTableSelectionMenu() {
    System.out.println("============= TABLE SELECTION =============");
    System.out.println("1. Type table name");
    System.out.println("2. Use list of table names from file");
    System.out.println("3. Back");
    System.out.print("Select an option > ");
  }

  private static Pair chooseInsertColumns(Scanner scanner) {
    boolean exit = false;

    do {
      displayInsertColumnsSelectionMenu();
      try {
        int input = Integer.parseInt(scanner.nextLine());
        System.out.println();
        switch (input) {
          case 1:
            System.out.print("Type column names separated by commas: ");
            String columns = scanner.nextLine();
            System.out.println();
            return new Pair(false, columns);
          case 2:
            System.out.print("Type the file name with file extension (file must be in root folder): ");
            String fileName = scanner.nextLine();
            System.out.println();
            return new Pair(true, fileName);
          case 3:
            exit = true;
            break;
          default:
            System.err.println("Please enter a valid option");
            System.out.println();
            break;
        }
      } catch (NumberFormatException e) {
        System.err.println("Please enter a valid input");
        System.out.println();
      }
    } while (!exit);
    return null;
  }

  private static void displayInsertColumnsSelectionMenu() {
    System.out.println("============= COLUMN SELECTION =============");
    System.out.println("1. Type column names");
    System.out.println("2. Use list of columns from file (the columns must correspond to the selected tables)");
    System.out.println("3. Back");
    System.out.print("Select an option > ");
  }

  private static Pair chooseInsertValues(Scanner scanner) {
    boolean exit = false;

    do {
      displayInsertValuesSelectionMenu();
      try {
        int input = Integer.parseInt(scanner.nextLine());
        System.out.println();
        switch (input) {
          case 1:
            System.out.print("Type values separated by commas: ");
            String values = scanner.nextLine();
            System.out.println();
            return new Pair(false, values);
          case 2:
            System.out.print("Type the file name with file extension (file must be in root folder): ");
            String fileName = scanner.nextLine();
            System.out.println();
            return new Pair(true, fileName);
          case 3:
            exit = true;
            break;
          default:
            System.err.println("Please enter a valid option");
            System.out.println();
            break;
        }
      } catch (NumberFormatException e) {
        System.err.println("Please enter a valid input");
        System.out.println();
      }
    } while (!exit);
    return null;
  }

  private static void displayInsertValuesSelectionMenu() {
    System.out.println("============= VALUE SELECTION =============");
    System.out.println("1. Type values");
    System.out.println("2. Use list of values from file (the number of values must correspond to the number of columns");
    System.out.println("3. Back");
    System.out.print("Select an option > ");
  }

  private static List<String> buildInsertSql(boolean hasFileInputTable, String tableOrFileName, Pair columnData, Pair valueData) {
    List<String> resultList = new ArrayList<String>();
    List<String> tableDataList = new ArrayList<String>();
    List<String> columnDataList = new ArrayList<String>();
    List<String> valueDataList = new ArrayList<String>();
    StringBuilder sqlStatement;

    System.out.println("Generating tables...");
    if (hasFileInputTable) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tableOrFileName))) {
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
          tableDataList.add(inputLine);
        }
      } catch (IOException e) {
        System.err.println("File: " + tableOrFileName + " is missing!");
        System.out.println();
      }
    } else {
      tableDataList.add(tableOrFileName);
    }
    System.out.println("Number of tables generated: " + tableDataList.size());

    System.out.println("Generating columns...");
    if ((boolean) columnData.getKey()) {
      String columnFileName = (String) columnData.getValue();
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(columnFileName))) {
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
          columnDataList.add(inputLine);
        }
      } catch (IOException e) {
        System.err.println("File: " + columnFileName + " is missing!");
        System.out.println();
      }
    } else {
      columnDataList.add((String) columnData.getValue());
    }
    System.out.println("Number of column sets generated: " + columnDataList.size());

    System.out.println("Generating values...");
    if ((boolean) valueData.getKey()) {
      String valueFileName = (String) valueData.getValue();
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(valueFileName))) {
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
          valueDataList.add(inputLine);
        }
      } catch (IOException e) {
        System.err.println("File: " + valueFileName + " is missing!");
        System.out.println();
      }
    } else {
      valueDataList.add((String) valueData.getValue());
    }
    System.out.println("Number of value sets generated: " + valueDataList.size());
    System.out.println();

    for (int i = 0; i < valueDataList.size(); i++) {
      String tableName;
      String columns;
      String values = valueDataList.get(i);

      if (tableDataList.size() > 1) {
        tableName = tableDataList.get(i).toUpperCase();
      } else {
        tableName = tableDataList.get(0).toUpperCase();
      }
      if (columnDataList.size() > 1) {
        columns = columnDataList.get(i).toUpperCase();
      } else {
        columns = columnDataList.get(0).toUpperCase();
      }

      sqlStatement = new StringBuilder("INSERT INTO ");
      sqlStatement.append(tableName + " (" + columns + ") VALUES (" + values + ");");
      String sqlString = sqlStatement.toString();
      // System.out.println("SQL Statement generated: " + sqlString); // For debugging
      resultList.add(sqlString);
    }
    return resultList;
  }

  private static void writeToFile(List<String> outputSql) {
    if (outputSql != null && outputSql.size() > 0) {
      try {
        Path path = Paths.get("output.sql");
        Files.write(path, outputSql, Charset.forName("UTF-8"));
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println();
      }
    } else {
      System.err.println("No SQL statements were generated!");
      System.out.println();
    }
  }
}
