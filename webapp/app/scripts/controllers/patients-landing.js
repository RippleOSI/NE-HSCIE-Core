'use strict';

angular.module('rippleDemonstrator')
        .controller('PatientsLandingCtrl', function ($scope, $stateParams, PatientLandingService) {
            
    
    PatientLandingService.all($stateParams.patientId).then(function(datasources) {
      $scope.datasources = datasources;
    });       
            
});

