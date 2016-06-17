'use strict';

angular.module('rippleDemonstrator')
  .controller('AlertsModalCtrl', function ($scope, $modalInstance, UserService, patient, alert, modal) {

    $scope.currentUser = UserService.getCurrentUser();

    $scope.alert = alert;
    $scope.patient = patient;
    $scope.modal = modal;

    if (modal.title === 'Create Alert') {
      $scope.alert.date = new Date().toISOString().slice(0, 10);
    }
    else {
      $scope.alert.date = new Date($scope.alert.date).toISOString().slice(0, 10);
    }

    $scope.ok = function (alertForm, alert) {
      $scope.formSubmitted = true;

      if (alertForm.$valid) {
        $modalInstance.close(alert);
      }
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.openDatepicker = function ($event, name) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope[name] = true;
    };

  });
