'use strict';

angular.module('rippleDemonstrator')
  .controller('MainSearchController', function($scope, $state, AdvancedSearch, Helper) {

    $scope.searchExpression = '';

    $scope.openAdvancedSearch = AdvancedSearch.openAdvancedSearch;

    $scope.searchFunction = function() {
      if ($scope.searchExpression.length > 0 && !isNaN($scope.searchExpression)) {
        AdvancedSearch.searchByDetails({nhsNumber: $scope.searchExpression}).then(function (result) {
          $scope.patients = result.data;
          changeState();
        });
      }
    };

    $scope.checkExpression = function(event) {
      if (!Helper.containsKeyCode(event.keyCode) && $scope.searchExpression.length > 0 && /^(?!.*[0-9])/.test($scope.searchExpression)) {
        AdvancedSearch.openAdvancedSearch($scope.searchExpression);
      }
    };

    var changeState = function() {
      $scope.formSubmitted = true;

      if ($scope.patients.length == 1) {
        $state.go('patients-summary', {
          patientId: $scope.patients[0].nhsNumber
        });
      }
      else if ($scope.patients.length > 1) {
        $state.go('patients-list', {
          patientsList: $scope.patients,
          advancedSearchParams: $scope.searchParams
        });
      }
      else {
        $state.go('patients-list', {
          patientsList: $scope.patients,
          advancedSearchParams: $scope.searchParams,
          displayEmptyTable: true
        });
      }
    };
  });

