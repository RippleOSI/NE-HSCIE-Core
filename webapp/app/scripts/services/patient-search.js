'use strict';

angular.module('rippleDemonstrator')
  .factory('PatientSearchService', function ($http) {
      
      var searchPatients = function (searchForm) {
      return $http.post('/api/search/patients', {searchParams : searchForm}).then(function (result) {
       
        return result.data;
      });
    };

    return {
      searchPatients: searchPatients
    };
          
});