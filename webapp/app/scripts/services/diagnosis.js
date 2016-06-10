'use strict';

angular.module('rippleDemonstrator')
  .factory('DiagnosesService', function ($http) {

    var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/diagnoses');
    };

    var get = function (patientId, diagnosesId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/diagnoses/' + diagnosesId + '?subSource=' + subSource);
    };

    return {
      all: all,
      get: get
    };

  });