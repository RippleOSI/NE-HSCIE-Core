'use strict';

angular.module('rippleDemonstrator')
  .controller('DiagnosesDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, DiagnosisService) {

    SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    DiagnosisService.get($stateParams.patientId, $stateParams.diagnosisIndex, $stateParams.source).then(function (result) {
      $scope.diagnosis = result.data;
      usSpinnerService.stop('diagnosisDetail-spinner');
    });

    $scope.isLocked = function (diagnosis) {
      if (!(diagnosis && diagnosis.id)) {
        return true;
      }

      var diagnosisIdSegments = contact.id.toString().split('::');
      if (contactIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(contactIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });