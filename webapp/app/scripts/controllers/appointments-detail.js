'use strict';

angular.module('rippleDemonstrator')
  .controller('AppointmentDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, AppointmentService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    AppointmentService.get($stateParams.patientId, $stateParams.appointmentIndex, $stateParams.source).then(function (result) {
      $scope.appointment = result.data;
      usSpinnerService.stop('appointmentDetail-spinner');
    });

    $scope.isLocked = function (appointment) {
      if (!(appointment && appointment.id)) {
        return true;
      }

      var appointmentIdSegments = appointment.id.toString().split('::');
      if (appointmentIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(appointmentIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });