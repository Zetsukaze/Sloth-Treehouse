import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class RegexSelector {
  public static void main (String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<String> matchedStrings = new ArrayList<String>();

    System.out.println();
    System.out.print("Enter filename with extension: ");
    String fileName = scanner.nextLine();
    Pattern pattern = Pattern.compile("CREATE TABLE (\\w+)");
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      String inputLine;
      while ((inputLine = bufferedReader.readLine()) != null) {
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
          matchedStrings.add(matcher.group(1));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Path path = Paths.get("output.txt");
      Files.write(path, matchedStrings, Charset.forName("UTF-8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
