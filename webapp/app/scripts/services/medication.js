'use strict';

angular.module('rippleDemonstrator')
  .factory('MedicationService', function ($http) {
    
     var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/medications');
    };

    var get = function (patientId, medicationId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/medications/' + medicationId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
