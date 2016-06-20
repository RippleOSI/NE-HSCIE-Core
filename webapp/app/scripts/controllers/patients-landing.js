'use strict';

angular.module('rippleDemonstrator')
  .controller('PatientsLandingCtrl', function ($scope, $stateParams, PatientLandingService, PatientService, UserService) {

    PatientService.get($stateParams.patientId, 'tie').then(function (result) {
      $scope.patient = result;
    });

    UserService.findCurrentUser().then(function (response) {
      $scope.currentUser = response.data;
      $stateParams.patientSource = $scope.currentUser.feature.patientSource;
    });

    PatientLandingService.all($stateParams.patientId).then(function (datasources) {
      $scope.datasources = datasources;
    });

  });

