'use strict';

angular.module('rippleDemonstrator')
  .controller('ResultsListCtrl', function ($scope, $filter, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, ResultService, UserService) {

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
    var date = $filter('date')(row.dateCreated, "dd/MM/yyyy");
    var taken = $filter('date')(row.sampleTaken, "dd/MM/yyyy");

      return (
        angular.lowercase(row.testName).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(date).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(taken).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    ResultService.all($stateParams.patientId).then(function (result) {
      $scope.results = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, resultSource) {
      $state.go('results-detail', {
        patientId: $scope.patient.nhsNumber,
        resultIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: resultSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (resultIndex) {
      return resultIndex === $stateParams.resultIndex;
    };

  });
