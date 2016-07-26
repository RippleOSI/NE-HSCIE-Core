'use strict';

angular.module('rippleDemonstrator')
  .factory('OrderService', function ($http) {

    
     var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/orders');
    };

    var get = function (patientId, orderId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/orders/' + orderId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
