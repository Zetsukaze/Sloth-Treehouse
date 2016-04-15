$scope.isValidSingaporeNricFin = function (nricFin) {
   var checkSum = 0;
   var nricFinStr = nricFin.toUpperCase();
   var nricFinPrefix = nricFinStr.substring(0,1);
   var nricFinFirstDigit = Number(nricFinStr.substring(1,2));
   var nricFinSecondDigit = Number(nricFinStr.substring(2,3));
   var nricFinThirdDigit = Number(nricFinStr.substring(3,4));
   var nricFinFourthDigit = Number(nricFinStr.substring(4,5));
   var nricFinFifthDigit = Number(nricFinStr.substring(5,6));
   var nricFinSixthDigit = Number(nricFinStr.substring(6,7));
   var nricFinSeventhDigit = Number(nricFinStr.substring(7,8));
   var nricFinSuffix = nricFinStr.substring(8,9);

   if (nricFinPrefix != "S" && nricFinPrefix != "T" && nricFinPrefix != "F" && nricFinPrefix != "G") {
      $scope.invalidNricFin = true;
      return false;
   }

   var isValidSuffix = false;
   var nricAllowedSuffix = ["J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"];
   var finAllowedSuffix = ["X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"];

   nricAllowedSuffix.forEach(function (allowedSuffix) {
      if (nricFinSuffix == allowedSuffix) {
         isValidSuffix = true;
      }
   });
   finAllowedSuffix.forEach(function (allowedSuffix) {
      if (nricFinSuffix == allowedSuffix) {
         isValidSuffix = true;
      }
   });

   if(!isValidSuffix) {
      $scope.invalidNricFin = true;
      return false;
   }

   if (nricFinPrefix == "T" || nricFinPrefix == "G") {
      checkSum += 4;
   }

   checkSum += (2 * nricFinFirstDigit);
   checkSum += (7 * nricFinSecondDigit);
   checkSum += (6 * nricFinThirdDigit);
   checkSum += (5 * nricFinFourthDigit);
   checkSum += (4 * nricFinFifthDigit);
   checkSum += (3 * nricFinSixthDigit);
   checkSum += (2 * nricFinSeventhDigit);

   checkSum = checkSum % 11;
   if (nricFinPrefix == "S" || nricFinPrefix == "T") {
      if (nricFinSuffix != nricAllowedSuffix[checkSum]) {
         $scope.invalidNricFin = true;
         return false;
      }
   } else {
      if (nricFinSuffix != finAllowedSuffix[checkSum]) {
         $scope.invalidNricFin = true;
         return false;
      }
   }
   return true;
};
