'use strict';

angular.module('rippleDemonstrator')
  .controller('TermsAndConditionsCtrl', function ($scope, $rootScope, $window, $modalInstance, modal, UserService) {

    $scope.modal = modal;

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');

      UserService.logout().then(function (response) {
        var redirectUrl = response.headers().location;

        if (redirectUrl) {
          $window.location = redirectUrl;
        }
      });
    };

    $scope.proceed = function () {
      $rootScope.termsAcknowledged = true;
      $modalInstance.dismiss('proceed');
    };

  });
