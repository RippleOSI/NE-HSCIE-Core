'use strict';

angular.module('rippleDemonstrator')
  .factory('ReferralService', function ($http) {
    
     var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/referrals');
    };

    var get = function (patientId, referralId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/referrals/' + referralId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
