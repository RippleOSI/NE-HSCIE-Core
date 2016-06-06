'use strict';

angular.module('rippleDemonstrator')
  .factory('TransferOfCareService', function ($http) {

    
     var all = function (patientId) {
      return $http.get('/api/patients/' + patientId + '/hscie-transfers');
    };

    var get = function (patientId, transferId, subSource) {
      return $http.get('/api/patients/' + patientId + '/hscie-transfers/' + transferId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
