'use strict';

angular.module('rippleDemonstrator')
  .factory('Notifications', function($http) {

    var all = function () {
      return $http.get('/api/notifications/sources');
    };

    var acknowledge = function (sourceId) {
      return $http.post('/api/notifications/' + sourceId + '/acknowledge');
    };

    return {
      all: all,
      acknowledge: acknowledge
    };
  });
