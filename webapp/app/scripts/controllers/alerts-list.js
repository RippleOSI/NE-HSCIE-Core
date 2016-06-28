'use strict';

angular.module('rippleDemonstrator')
  .controller('AlertListCtrl', function ($scope, $filter, $state, $location, $stateParams, SearchInput, $modal, usSpinnerService, PatientService, AlertService, UserService) {

    SearchInput.update();
    $scope.currentPage = 1;

    UserService.findCurrentUser().then(function (response) {
      $scope.currentUser = response.data;
      $stateParams.patientSource = $scope.currentUser.feature.patientSource;
    });

    $scope.pageChangeHandler = function (newPage) {
      $scope.currentPage = newPage;
    };

    if ($stateParams.page) {
      $scope.currentPage = $stateParams.page;
    }

    $scope.search = function (row) {

      return (
        angular.lowercase(row.alertType).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1 
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    AlertService.all($stateParams.patientId).then(function (result) {
      $scope.alerts = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, alertSource) {
      $state.go('alerts-detail', {
        patientId: $scope.patient.nhsNumber,
        alertIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: alertSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (alertIndex) {
      return alertIndex === $stateParams.alertIndex;
    };

  });
