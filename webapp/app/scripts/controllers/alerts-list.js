'use strict';

angular.module('rippleDemonstrator')
  .controller('AlertsListCtrl', function ($scope, $location, $stateParams, $modal, $state, usSpinnerService, PatientService, AlertService) {

    $scope.currentPage = 1;

    $scope.pageChangeHandler = function (newPage) {
      $scope.currentPage = newPage;
    };

    if ($stateParams.page) {
      $scope.currentPage = $stateParams.page;
    }

    $scope.search = function (row) {
      return (
        angular.lowercase(row.alertType).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        //angular.lowercase(row.dateTime).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    AlertService.findAllSummaries($stateParams.patientId).then(function (result) {
      $scope.alerts = result.data;

      for (var i = 0; i < $scope.alerts.length; i++) {
        $scope.alerts[i].dateTime = moment($scope.alerts[i].dateTime).format('DD-MMM-YYYY h:mm a');
      }

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (sourceId) {
      $state.go('alerts-detail', {
        patientId: $scope.patient.nhsNumber,
        alertId: sourceId,
        filter: $scope.query,
        page: $scope.currentPage
      });
    };

    $scope.selected = function ($index) {
      return $index === $stateParams.alertId;
    };

    $scope.create = function () {
      var modalInstance = $modal.open({
        templateUrl: 'views/alerts/alerts-modal.html',
        size: 'lg',
        controller: 'AlertsModalCtrl',
        resolve: {
          modal: function () {
            return {
              title: 'Create Alert'
            };
          },
          alert: function () {
            return {};
          },
          patient: function () {
            return $scope.patient;
          }
        }
      });

      modalInstance.result.then(function (alert) {
        var toAdd = {
          type: alert.type,
          note: alert.note,
          date: alert.dateTime,
          author: alert.author
        };

        AlertService.create($scope.patient.id, toAdd).then(function () {
          setTimeout(function () {
            $state.go('alerts', {
              patientId: $scope.patient.id,
              filter: $scope.query,
              page: $scope.currentPage
            }, {
              reload: true
            });
          }, 2000);
        });
      });
    };

  });
