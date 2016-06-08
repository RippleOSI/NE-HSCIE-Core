'use strict';

angular.module('rippleDemonstrator')
  .factory('AppointmentService', function ($http) {

    
     var all = function (patientId) {
      return $http.get('/api/hscie/patients/' + patientId + '/appointments');
    };

    var get = function (patientId, appointmentId, subSource) {
      return $http.get('/api/hscie/patients/' + patientId + '/appointments/' + appointmentId + '?subSource=' + subSource);
    };


    return {
      get: get,
      all: all
    };

  });
