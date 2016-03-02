'use strict';

angular.module('rippleDemonstrator')
  .factory('PatientSearchService', function ($http) {

    var searchPatients = function (searchForm) {
      return $http.post('/api/search/patients', {searchParams: searchForm});
    };

    return {
      searchPatients: searchPatients
    };

  });
