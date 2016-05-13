public class NricFinValidator {
  public static void main(String[] args) {
    String nricFin = args[0];

    if (nricFin == null || nricFin.length() != 9) {
      System.out.println("Invalid NRIC/FIN length.");
    }
    nricFin = nricFin.toUpperCase();

    int checkSum = 0;
    int[] checkSumWeightageArray = {2, 7, 6, 5, 4, 3, 2};
    boolean validSuffix = false;
    char[] nricAllowedSuffixes = {'J', 'Z', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
    char[] finAllowedSuffixes = {'X', 'W', 'U', 'T', 'R', 'Q', 'P', 'N', 'M', 'L', 'K'};
    char[] nricFinArray = nricFin.toCharArray();
    char nricFinPrefix = nricFinArray[0];
    char nricFinSuffix = nricFinArray[8];

    if (nricFinPrefix != 'S' && nricFinPrefix != 'T' && nricFinPrefix != 'F' && nricFinPrefix != 'G') {
      System.out.println("Invalid NRIC/FIN prefix.");
    }
    for (char c: nricAllowedSuffixes) {
      if (c == nricFinSuffix) {
        validSuffix = true;
      }
    }
    for (char c: finAllowedSuffixes) {
      if (c == nricFinSuffix) {
        validSuffix = true;
      }
    }
    if (!validSuffix) {
      System.out.println("Invalid NRIC/FIN suffix.");
    }

    // Calculation of check letter
    if (nricFinPrefix == 'T' || nricFinPrefix == 'G') {
      checkSum += 4;
    }
    for (int i = 0; i < 7; i++) {
      checkSum += checkSumWeightageArray[i] * Character.getNumericValue(nricFinArray[i + 1]);
    }
    checkSum = checkSum % 11;

    if (nricFinPrefix == 'S' || nricFinPrefix == 'T') {
      if (nricFinSuffix != nricAllowedSuffixes[checkSum]) {
        System.out.println("Invalid NRIC check letter. Letter should be: " + nricAllowedSuffixes[checkSum]);
      }
    } else {
      if (nricFinSuffix != finAllowedSuffixes[checkSum]) {
        System.out.println("Invalid FIN check letter. Letter should be: " + finAllowedSuffixes[checkSum]);
      }
    }
  }
}
