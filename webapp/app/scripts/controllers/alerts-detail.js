'use strict';

angular.module('rippleDemonstrator')
  .controller('AlertsDetailCtrl', function ($scope, $stateParams, $modal, $state, $location, usSpinnerService, PatientService, AlertService) {

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    AlertService.findDetails($stateParams.patientId, $stateParams.alertId).then(function (result) {
      $scope.alert = result.data;

      usSpinnerService.stop('alertsDetail-spinner');
    });

    $scope.edit = function () {
      var modalInstance = $modal.open({
        templateUrl: 'views/alerts/alerts-modal.html',
        size: 'lg',
        controller: 'AlertsModalCtrl',
        resolve: {
          modal: function () {
            return {
              title: 'Edit Alert'
            };
          },
          alert: function () {
            return angular.copy($scope.alert);
          },
          patient: function () {
            return $scope.patient;
          }
        }
      });

      modalInstance.result.then(function (alert) {
        var toUpdate = {
          source: alert.source,
          sourceId: alert.sourceId,
          type: alert.type,
          note: alert.note,
          date: alert.dateTime,
          author: alert.author
        };

        AlertService.update($scope.patient.id, toUpdate).then(function () {
          setTimeout(function () {
            $state.go('alerts-detail', {
              patientId: $scope.patient.id,
              alertId: alert.sourceId,
              page: $scope.currentPage
            });
          }, 2000);
        });
      });
    };

  });
