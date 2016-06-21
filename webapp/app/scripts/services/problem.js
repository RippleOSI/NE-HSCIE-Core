'use strict';

angular.module('rippleDemonstrator')
  .factory('ProblemsService', function ($http) {

    var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/problems');
    };

    var get = function (patientId, problemId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/problems/' + problemId + '?subSource=' + subSource);
    };

    return {
      all: all,
      get: get
    };

  });