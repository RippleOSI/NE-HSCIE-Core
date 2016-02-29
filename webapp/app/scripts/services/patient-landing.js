'use strict';

angular.module('rippleDemonstrator')
  .factory('PatientLandingService', function ($http) {
      
      var all = function (id) {
      return $http.get('/api/patients/' + id + '/datasources').then(function (result) {
       
        return result.data;
      });
    };

    return {
      all: all
    };
          
});
