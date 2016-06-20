'use strict';

angular.module('rippleDemonstrator')
  .controller('ProblemsListCtrl', function ($scope, $filter, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, ProblemsService, UserService) {

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
    var date = $filter('date')(row.dateOfOnset, "dd/MM/yyyy HH:mm");
      return (
        angular.lowercase(row.problem).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(date).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.sourceId).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

   ProblemsService.all($stateParams.patientId).then(function (result) {
      $scope.problems = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, problemSource) {
      $state.go('problems-detail', {
        patientId: $scope.patient.nhsNumber,
        problemIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: problemSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (problemIndex) {
      return problemIndex === $stateParams.problemIndex;
    };

  });
