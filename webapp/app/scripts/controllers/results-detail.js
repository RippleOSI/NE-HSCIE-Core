'use strict';

angular.module('rippleDemonstrator')
  .controller('ResultsDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, ResultService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    ResultService.get($stateParams.patientId, $stateParams.resultIndex, $stateParams.source).then(function (result) {
      $scope.result = result.data;
      usSpinnerService.stop('resultsDetail-spinner');
    });

    $scope.isLocked = function (result) {
      if (!(result && result.id)) {
        return true;
      }

      var resultIdSegments = result.id.toString().split('::');
      if (resultIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(resultIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });