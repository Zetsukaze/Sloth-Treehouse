public class NricFinValidator {
  public static void main(String[] args) {
    String nricFin = args[0];

    if (nricFin == null || nricFin.length() != 9) {
      System.out.println("Invalid NRIC/FIN length.");
    }
    nricFin = nricFin.toUpperCase();

    int checkSum = 0;
    boolean isValidSuffix = false;
    char[] nricAllowedSuffixes = {'J', 'Z', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
    char[] finAllowedSuffixes = {'X', 'W', 'U', 'T', 'R', 'Q', 'P', 'N', 'M', 'L', 'K'};
    char[] nricFinArray = nricFin.toCharArray();
    char nricFinPrefix = nricFinArray[0];
    int nricFinFirstDigit = Character.getNumericValue(nricFinArray[1]);
    int nricFinSecondDigit = Character.getNumericValue(nricFinArray[2]);
    int nricFinThirdDigit = Character.getNumericValue(nricFinArray[3]);
    int nricFinFourthDigit = Character.getNumericValue(nricFinArray[4]);
    int nricFinFifthDigit = Character.getNumericValue(nricFinArray[5]);
    int nricFinSixthDigit = Character.getNumericValue(nricFinArray[6]);
    int nricFinSeventhDigit = Character.getNumericValue(nricFinArray[7]);
    char nricFinSuffix = nricFinArray[8];

    if (nricFinPrefix != 'S' && nricFinPrefix != 'T' && nricFinPrefix != 'F' && nricFinPrefix != 'G') {
      System.out.println("Invalid NRIC/FIN prefix.");
    }
    for (char c: nricAllowedSuffixes) {
      if (c == nricFinSuffix) {
        isValidSuffix = true;
      }
    }
    for (char c: finAllowedSuffixes) {
      if (c == nricFinSuffix) {
        isValidSuffix = true;
      }
    }
    if (!isValidSuffix) {
      System.out.println("Invalid NRIC/FIN suffix.");
    }

    // Calculation of check letter
    if (nricFinPrefix == 'T' || nricFinPrefix == 'G') {
      checkSum += 4;
    }
    checkSum += (2 * nricFinFirstDigit);
    checkSum += (7 * nricFinSecondDigit);
    checkSum += (6 * nricFinThirdDigit);
    checkSum += (5 * nricFinFourthDigit);
    checkSum += (4 * nricFinFifthDigit);
    checkSum += (3 * nricFinSixthDigit);
    checkSum += (2 * nricFinSeventhDigit);
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
