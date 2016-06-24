'use strict';

angular.module('rippleDemonstrator')
  .controller('TransferOfCareListCtrl', function ($scope, $state, $stateParams, SearchInput, $location, $modal, usSpinnerService, PatientService, TransferOfCareService, UserService) {

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
        angular.lowercase(row.siteFrom).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.siteTo).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.dateOfTransfer).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.source).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };


    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    TransferOfCareService.all($stateParams.patientId).then(function (result) {
      $scope.transfers = result.data;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (id, transferSource) {
      $state.go('transferOfCare-detail', {
        patientId: $scope.patient.nhsNumber,
        transferIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: transferSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.selected = function (transferIndex) {
      return transferIndex === $stateParams.transferIndex;
    };

 $scope.careOrder = function(transfer){
	    if(transfer.careType.indexOf("Admission") > -1){
	    	return 0;
	    }
	    if(transfer.careType.indexOf("Transfer") > -1){
	    	return 1;
	    }
	    if(transfer.careType.indexOf("Discharge") > -1){
	    	return 2;
	    }
    }

  });
