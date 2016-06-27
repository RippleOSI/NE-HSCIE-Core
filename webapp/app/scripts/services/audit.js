'use strict';

angular.module('rippleDemonstrator')
  .factory('AuditService', function ($http, Audit) {

    var allByPatient = function (patientId, page) {
        return $http.get('/api/audit?patientId=' + patientId + '&page=' + page).then(function(result) {
        	return convertAudits(result.data);
        });
    };   
      
    var allByUsername = function (username, page) {
        return $http.get('/api/audit?username=' + username + '&page=' + page).then(function(result) {
        	return convertAudits(result.data);
        });
    };   
    
    var countByPatient = function (patientId) {
        return $http.get('/api/audit/count?patientId=' + patientId);
    };   
      
    var countByUsername = function (username) {
        return $http.get('/api/audit/count?username=' + username);
    };  
    
    var get = function (auditId) {
        return $http.get('/api/audit/' + auditId).then(function(result) {
        	return new Audit(result.data);
        });
    };  
    
    function convertAudits(rawAudits) {
    	var audits = [];
    	
        angular.forEach(rawAudits, function (rawAudit) {
            audits.push(new Audit(rawAudit));
          });
        
        return audits;
    };

    return {
      allByPatient: allByPatient,
      allByUsername: allByUsername,
      countByPatient: countByPatient,
      countByUsername: countByUsername,
      get: get
    };

  });
