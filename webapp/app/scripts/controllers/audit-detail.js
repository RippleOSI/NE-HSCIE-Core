'use strict';

angular.module('rippleDemonstrator')
  .controller('AuditDetailCtrl', function ($scope, $stateParams, SearchInput, $modal, $state, $location, usSpinnerService, AuditService) {

    SearchInput.update();

    $scope.init = function(){
    	if($stateParams.auditId !== undefined){
    		$scope.getAudit($stateParams.auditId); 
    	}
    }; 
    
    $scope.getAudit = function(auditId){
	    AuditService.get(auditId).then(function (result) {
	        $scope.audit = result.data;
	        usSpinnerService.stop('auditDetail-spinner');
	      });
    };
    
    $scope.$on('audits:get-audit', function(event, args) {

    	$scope.getAudit(args.auditId); 
    });
    

  });
