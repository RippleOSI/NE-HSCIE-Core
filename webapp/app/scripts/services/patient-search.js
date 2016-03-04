'use strict';

angular.module('rippleDemonstrator')
  .factory('PatientSearchService', function ($http) {

    var searchPatients = function (searchForm, source) {
      return $http.post('/api/patients/search' + '?source=' + source, searchForm);
    };

    return {
      searchPatients: searchPatients
    };
  });
