'use strict';

angular.module('rippleDemonstrator')
  .controller('AuditListCtrl', function ($rootScope, $scope, $location, $stateParams, SearchInput, $modal, $state, usSpinnerService, AuditService) {

    SearchInput.update();

    $scope.pageChangeHandler = function (newPage) {
      $scope.currentPage = newPage;
    };
  
    // Pagination
    $scope.auditsPerPage = 15;
    $scope.totalAudits = 0;
    
    var firstPage = 1;
    
    $scope.pagination = {
    	current: 1
    };
    
    $scope.onSearch = function() {
        getResultsPage(firstPage);
    };
    
    $scope.onPageChanged = function(newPage) {
        getResultsPage(newPage);
    };
    
    function getResultsPage(pageNumber) {

		AuditService.countByPatient($scope.searchTerm).then(function (result) {
			$scope.totalAudits = result.data;
	    }); 
    	
		usSpinnerService.spin('auditSummary-spinner');
	  		
		AuditService.allByPatient($scope.searchTerm, pageNumber).then(function (result) {
          $scope.audits = result.data;
          usSpinnerService.stop('auditSummary-spinner');
		});    	
    }    
    
    // Detail drill down
    $scope.go = function (id) {
    	$rootScope.$broadcast('audits:get-audit', { auditId: id });
    	
//      $state.go('audit-detail', {
//        auditId: id,
//        page: $scope.currentPage
//      });
    };
    
  });
