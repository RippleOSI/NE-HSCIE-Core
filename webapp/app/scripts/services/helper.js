'use strict';

angular.module('rippleDemonstrator')
  .factory('Helper', function (keyCodes) {


    var updateId = function (sourceId) {
      var sourceArr = sourceId.split('::');
      var newVersionNumber = parseInt(sourceArr[2]) + 1;
      var newId = sourceArr[0] + '::' + sourceArr[1] + '::' + newVersionNumber;
      return newId;
    };

    var containsKeyCode = function(keyCode) {
      var contains = false;
      for (var key in keyCodes) {
        if (keyCodes.hasOwnProperty(key) && keyCodes[key] === keyCode) {
          contains = true;
        }
      }

      return contains;
    };

    return {
      updateId: updateId,
      containsKeyCode: containsKeyCode
    };

  });
