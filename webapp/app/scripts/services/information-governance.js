'use strict';

angular.module('rippleDemonstrator')
  .factory('InformationGovernance', function($http) {

    var all = function (patientId) {
      return $http.get('/api/patients/' + patientId + '/consents');
    };

    var get = function (patientId, consentId) {
      return $http.get('/api/patients/' + patientId + '/consents/' + consentId);
    };

    var create = function (patientId, consentId) {
      return $http.post('/api/patients/' + patientId + '/consents', consentId);
    };

    var update = function (patientId, consentId) {
      return $http.put('/api/patients/' + patientId + '/consents', consentId);
    };

    return {
      all: all,
      get: get,
      update: update,
      create: create
    };

  });

