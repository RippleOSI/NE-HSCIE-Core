'use strict';

angular.module('rippleDemonstrator')
  .controller('ContactsListCtrl', function ($scope, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, ContactsService, UserService) {

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
        angular.lowercase(row.name).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.relationship).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.nextOfKin).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    ContactsService.all($stateParams.patientId).then(function (result) {
      $scope.contacts = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, contactSource) {
      $state.go('contacts-detail', {
        patientId: $scope.patient.nhsNumber,
        contactIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: contactSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (contactIndex) {
      return contactIndex === $stateParams.contactIndex;
    };

  });
