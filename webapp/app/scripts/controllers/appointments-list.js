'use strict';

angular.module('rippleDemonstrator')
  .controller('AppointmentListCtrl', function ($scope, $filter, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, AppointmentService, UserService) {

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
    var date = $filter('date')(row.dateOfAppointment, "dd/MM/yyyy");
    var time = $filter('date')(row.timeOfAppointment, "HH:mm");

      return (
        angular.lowercase(row.serviceTeam).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(date).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(time).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    AppointmentService.all($stateParams.patientId).then(function (result) {
      $scope.appointments = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, appointmentSource) {
      $state.go('appointments-detail', {
        patientId: $scope.patient.nhsNumber,
        appointmentIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: appointmentSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (appointmentIndex) {
      return appointmentIndex === $stateParams.appointmentIndex;
    };

  });
