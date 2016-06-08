'use strict';

angular.module('rippleDemonstrator')
  .factory('ContactsService', function ($http) {

    var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/contacts');
    };

    var get = function (patientId, contactId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/contacts/' + contactId + '?subSource=' + subSource);
    };

    return {
      all: all,
      get: get
    };

  });