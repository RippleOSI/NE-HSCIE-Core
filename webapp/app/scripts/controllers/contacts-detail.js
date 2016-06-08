'use strict';

angular.module('rippleDemonstrator')
  .controller('ContactsDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, ContactsService) {

    SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    ContactsService.get($stateParams.patientId, $stateParams.contactIndex, $stateParams.source).then(function (result) {
      $scope.contact = result.data;
      usSpinnerService.stop('contactDetail-spinner');
    });

    $scope.isLocked = function (contact) {
      if (!(contact && contact.id)) {
        return true;
      }

      var contactIdSegments = contact.id.toString().split('::');
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