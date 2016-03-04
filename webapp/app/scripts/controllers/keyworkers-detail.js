'use strict';

angular.module('rippleDemonstrator')
  .controller('KeyworkersDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, KeyworkersService) {

    SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    KeyworkersService.get($stateParams.patientId, $stateParams.keyworkerIndex, $stateParams.source).then(function (result) {
      $scope.keyworker = result.data;
      usSpinnerService.stop('keyworkerDetail-spinner');
    });

    $scope.isLocked = function (keyworker) {
      if (!(keyworker && keyworker.id)) {
        return true;
      }

      var keyworkerIdSegments = keyworker.id.toString().split('::');
      if (keyworkerIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(keyworkerIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });
