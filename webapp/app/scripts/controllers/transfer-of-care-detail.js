'use strict';

angular.module('rippleDemonstrator')
  .controller('TransferOfCareDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, TransferOfCareService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    TransferOfCareService.get($stateParams.patientId, $stateParams.transferIndex, $stateParams.source).then(function (result) {
      $scope.transfer = result.data;
      usSpinnerService.stop('transferDetail-spinner');
    });

    $scope.isLocked = function (transfer) {
      if (!(transfer && transfer.id)) {
        return true;
      }

      var transferIdSegments = transfer.id.toString().split('::');
      if (transferIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(transferIdSegments[1]) < 0);
      }

      return true;
    };
    
 

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });