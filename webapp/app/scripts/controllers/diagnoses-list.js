'use strict';

angular.module('rippleDemonstrator')
  .controller('DiagnosesListCtrl', function ($scope, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, DiagnosesService, UserService) {

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
    		  angular.lowercase(row.problem).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
    	        angular.lowercase(row.dateOfOnset).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
    	        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    DiagnosesService.all($stateParams.patientId).then(function (result) {
      $scope.diagnoses = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, diagnosesSource) {
      $state.go('diagnoses-detail', {
        patientId: $scope.patient.nhsNumber,
        diagnosesIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: diagnosesSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (diagnosesIndex) {
      return diagnosesIndex === $stateParams.diagnosesIndex;
    };

  });
