'use strict';

angular.module('rippleDemonstrator')
  .controller('KeyworkersListCtrl', function ($scope, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, KeyworkersService, UserService) {

    SearchInput.update();
    $scope.currentPage = 1;

    var currentUser = UserService.getCurrentUser();
    $stateParams.patientSource = currentUser.feature.patientSource;

    $scope.pageChangeHandler = function (newPage) {
      $scope.currentPage = newPage;
    };

    if ($stateParams.page) {
      $scope.currentPage = $stateParams.page;
    }

    $scope.search = function (row) {
      return (
        angular.lowercase(row.name).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.role).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.contactNumber).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    KeyworkersService.all($stateParams.patientId).then(function (result) {
      $scope.keyworkers = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, keyworkerSource) {
      $state.go('keyworkers-detail', {
        patientId: $scope.patient.nhsNumber,
        keyworkerIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: keyworkerSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (keyworkerIndex) {
      return keyworkerIndex === $stateParams.keyworkerIndex;
    };

  });
