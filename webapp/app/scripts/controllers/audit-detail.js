'use strict';

angular.module('rippleDemonstrator')
  .controller('AuditDetailCtrl', function ($scope, $stateParams, SearchInput, usSpinnerService, AuditService) {

    SearchInput.update();

    $scope.init = function(){
    	if($stateParams.auditId !== undefined){
    		$scope.getAudit($stateParams.auditId); 
    	}
    }; 
    
    $scope.getAudit = function(auditId){
    	usSpinnerService.spin('auditDetail-spinner');
    	
    	AuditService.get(auditId).then(function (result) {
    		$scope.audit = result;
	        usSpinnerService.stop('auditDetail-spinner');
	      });
    };
    
    $scope.$on('audits:get-audit', function(event, args) {
    	$scope.getAudit(args.auditId); 
    });  
    
  });
