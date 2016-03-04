'use strict';

angular.module('rippleDemonstrator')
  .controller('PatientsLandingCtrl', function ($scope, $stateParams, PatientLandingService, PatientService, UserService) {

    PatientService.get($stateParams.patientId, 'tie').then(function (result) {
      $scope.patient = result;
    });

    var currentUser = UserService.getCurrentUser();
    $stateParams.patientSource = currentUser.feature.patientSource;

    PatientLandingService.all($stateParams.patientId).then(function (datasources) {
      $scope.datasources = datasources;
    });

  });

