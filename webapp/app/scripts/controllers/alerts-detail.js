'use strict';

angular.module('rippleDemonstrator')
  .controller('AlertDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, AlertService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    AlertService.get($stateParams.patientId, $stateParams.alertIndex, $stateParams.source).then(function (result) {
      $scope.alert = result.data;
      usSpinnerService.stop('alertsDetail-spinner');
    });

    $scope.isLocked = function (alert) {
      if (!(alert && alert.id)) {
        return true;
      }

      var alertIdSegments = alert.id.toString().split('::');
      if (alertIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(alertIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });