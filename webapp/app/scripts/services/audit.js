'use strict';

angular.module('rippleDemonstrator')
  .factory('AuditService', function ($http) {

    var allByPatient = function (patientId, page) {
        return $http.get('/api/audit?patientId=' + patientId + '&page=' + page);
    };   
      
    var allByUsername = function (username, page) {
        return $http.get('/api/audit?username=' + username + '&page=' + page);
    };   
    
    var countByPatient = function (patientId) {
        return $http.get('/api/audit/count?patientId=' + patientId);
    };   
      
    var countByUsername = function (username) {
        return $http.get('/api/audit/count?username=' + username);
    };  
    
    var get = function (auditId) {
        return $http.get('/api/audit/' + auditId);
    };      

    return {
      allByPatient: allByPatient,
      allByUsername: allByUsername,
      countByPatient: countByPatient,
      countByUsername: countByUsername,
      get: get
    };

  });
