'use strict';

angular.module('rippleDemonstrator')
  .controller('ProblemsDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, ProblemsService) {

    SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    ProblemsService.get($stateParams.patientId, $stateParams.problemIndex, $stateParams.source).then(function (result) {
      $scope.problem = result.data;
      usSpinnerService.stop('problemDetail-spinner');
    });

    $scope.isLocked = function (problem) {
      if (!(problem && problem.id)) {
        return true;
      }

      var problemIdSegments = problem.id.toString().split('::');
      if (problemIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(problemIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });