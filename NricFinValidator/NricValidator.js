function isValidSingaporeNricFin(nricFin) {
  if (!nricFin || nricFin.length != 9) {
    return false;
  }
  var nricFinStr = nricFin.toUpperCase();

  var checkSum = 0;
  var checkSumWeightageArray = [2, 7, 6. 5, 4, 3, 2];
  var validSuffix = false;
  var nricAllowedSuffix = ["J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"];
  var finAllowedSuffix = ["X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"];
  var nricFinPrefix = nricFinStr.subString(0, 1);
  var nricFinSuffix = nricFinStr.subString(8, 9);

  if (nricFinPrefix != "S" && nricFinPrefix != "T" && nricFinPrefix != "F" && nricFinPrefix != "G") {
    return false;
  }
  nricAllowedSuffix.forEach(function (allowedSuffix) {
    if (nricFinSuffix == allowedSuffix) {
      validSuffix = true;
    }
  });
  finAllowedSuffix.forEach(function (allowedSuffix) {
    if (nricFinSuffix == allowedSuffix) {
      validSuffix = true;
    }
  });
  if (!validSuffix) {
    return false;
  }

  // Calculation of check letter
  if (nricFinPrefix == "T" || nricFinPrefix == "G") {
    checkSum += 4;
  }
  for (var i = 0; i < 7; i++) {
    checkSum += checkSumWeightageArray[i] * Number(nricFinStr.subString(i + 1, i + 2));
  }
  checkSum = checkSum % 11;

  if (nricFinPrefix == "S" || nricFinPrefix == "T") {
    if (nricFinSuffix != nricAllowedSuffix[checkSum]) {
      return false;
    }
  } else {
    if (nricFinSuffix != finAllowedSuffix[checkSum]) {
      return false;
    }
  }
  return true;
}
