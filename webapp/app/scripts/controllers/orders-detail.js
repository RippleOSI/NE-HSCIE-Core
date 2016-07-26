'use strict';

angular.module('rippleDemonstrator')
  .controller('OrdersDetailCtrl', function ($scope, $stateParams, SearchInput, $location, $modal, Helper, $state, usSpinnerService, PatientService, OrderService) {

  
   SearchInput.update();
    $scope.UnlockedSources = [
      'handi.ehrscape.com'
    ];

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    OrderService.get($stateParams.patientId, $stateParams.orderIndex, $stateParams.source).then(function (result) {
      $scope.order = result.data;
      usSpinnerService.stop('orderDetail-spinner');
    });

    $scope.isLocked = function (order) {
      if (!(order && order.id)) {
        return true;
      }

      var orderIdSegments = order.id.toString().split('::');
      if (orderIdSegments.length > 1) {
        return ($scope.UnlockedSources.indexOf(orderIdSegments[1]) < 0);
      }

      return true;
    };

    $scope.convertToLabel = function (text) {
      var result = text.replace(/([A-Z])/g, ' $1');
      return result.charAt(0).toUpperCase() + result.slice(1);
    };

  });