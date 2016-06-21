'use strict';

angular.module('rippleDemonstrator')
  .controller('ReferralListCtrl', function ($scope, $filter, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, ReferralService, UserService) {

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
    var date = $filter('date')(row.dateOfReferral, "dd/MM/yyyy");

      return (
        angular.lowercase(row.referralFrom).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.referralTo).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(date).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    ReferralService.all($stateParams.patientId).then(function (result) {
      $scope.referrals = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, referralSource) {
      $state.go('referrals-detail', {
        patientId: $scope.patient.nhsNumber,
        referralIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: referralSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (referralIndex) {
      return referralIndex === $stateParams.referralIndex;
    };

  });
