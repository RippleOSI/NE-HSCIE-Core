'use strict';

angular.module('rippleDemonstrator')
  .controller('MedicationsDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, MedicationService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    MedicationService.get($stateParams.patientId, $stateParams.medicationIndex, $stateParams.source).then(function (result) {
      $scope.medication = result.data;
      usSpinnerService.stop('medicationsDetail-spinner');
    });

    $scope.isLocked = function (medication) {
      if (!(medication && medication.id)) {
        return true;
      }

      var medicationIdSegments = medication.id.toString().split('::');
      if (medicationIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(medicationIdSegments[1]) < 0);
      }

      return true;
    };
    
 

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });