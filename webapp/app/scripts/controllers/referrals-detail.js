'use strict';

angular.module('rippleDemonstrator')
  .controller('ReferralDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, ReferralService) {

   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    ReferralService.get($stateParams.patientId, $stateParams.referralIndex, $stateParams.source).then(function (result) {
      $scope.referral = result.data;
      usSpinnerService.stop('referralsDetail-spinner');
    });

    $scope.isLocked = function (referral) {
      if (!(referral && referral.id)) {
        return true;
      }

      var referralIdSegments = referral.id.toString().split('::');
      if (referralIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(referralIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });
