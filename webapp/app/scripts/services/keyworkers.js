'use strict';

angular.module('rippleDemonstrator')
  .factory('KeyworkersService', function ($http) {

    var all = function (patientId) {
      return $http.get('/api/patients/' + patientId + '/keyworkers');
    };

    var get = function (patientId, keyworkerId, subSource) {
      return $http.get('/api/patients/' + patientId + '/keyworkers/' + keyworkerId + '?subSource=' + subSource);
    };

    return {
      all: all,
      get: get
    };

  });

