'use strict';

angular.module('rippleDemonstrator')
  .controller('OrdersListCtrl', function ($scope, $filter, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, OrderService, UserService) {

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
    var date = $filter('date')(row.orderDate, "dd/MM/yyyy");

      return (
        angular.lowercase(row.name).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(date).indexOf(angular.lowercase($scope.query) || '') !== -1 
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    OrderService.all($stateParams.patientId).then(function (result) {
      $scope.orders = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, orderSource) {
      $state.go('orders-detail', {
        patientId: $scope.patient.nhsNumber,
        orderIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: orderSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (orderIndex) {
      return orderIndex === $stateParams.orderIndex;
    };

  });
