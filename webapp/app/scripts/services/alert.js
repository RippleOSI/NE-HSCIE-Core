'use strict';

angular.module('rippleDemonstrator')
  .factory('AlertService', function ($http) {

    var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/alerts');
    };

    var get = function (patientId, alertId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/alerts/' + alertId + '?subSource=' + subSource);
    };

    return {
      all: all,
      get: get
    };

  });
