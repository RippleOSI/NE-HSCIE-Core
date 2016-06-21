'use strict';

angular.module('rippleDemonstrator')
  .factory('AlertService', function ($http) {

    var findAllSummaries = function (patientId) {
      return $http.get('/api/patients/' + patientId + '/alerts');
    };

    var findDetails = function (patientId, alertId) {
      return $http.get('/api/patients/' + patientId + '/alerts/' + alertId);
    };

    var create = function (patientId, alert) {
      return $http.post('/api/patients/' + patientId + '/alerts', alert);
    };

    var update = function (patientId, alert) {
      return $http.put('/api/patients/' + patientId + '/alerts', alert);
    };

    return {
      findAllSummaries: findAllSummaries,
      findDetails: findDetails,
      update: update,
      create: create
    };

  });
