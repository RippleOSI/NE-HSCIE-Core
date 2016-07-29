'use strict';

angular.module('rippleDemonstrator')
  .factory('ResultService', function ($http) {

    
     var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/results');
    };

    var get = function (patientId, resultId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/results/' + resultId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
