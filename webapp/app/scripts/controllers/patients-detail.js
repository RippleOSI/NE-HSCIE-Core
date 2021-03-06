'use strict';

angular.module('rippleDemonstrator')
  .controller('PatientsDetailCtrl', function ($scope, $stateParams, $state, SearchInput, PatientService, UserService) {

    SearchInput.update();
    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    UserService.findCurrentUser().then(function (response) {
      $scope.currentUser = response.data;
      $stateParams.patientSource = $scope.currentUser.feature.patientSource;

      var patientContextParams = UserService.getContent('patientContextParams');
      $scope.pasNumberLabel = patientContextParams.pasNumberLabel;
    });

    $scope.goTo = function (section) {
      var requestHeader = {
        patientId: $stateParams.patientId,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        patientSource: $stateParams.patientSource
      };

      var toState = '';

      switch (section) {
      case 'summary':
        toState = 'patients-summary';
        break;
      case 'alerts':
        toState = 'alerts-list';
        break;
      case 'keyworkers':
        toState = 'keyworkers-list';
        break;
      case 'contacts':
        toState = 'contacts-list';
        break;
      case 'problems':
        toState = 'problems-list';
        break;
      case 'allergies':
        toState = 'allergies';
        break;
      case 'medications':
        toState = 'medications';
        break;
      case 'orders':
        toState = 'orders';
        break;
      case 'results':
        toState = 'results';
        break;
      case 'procedures':
        toState = 'procedures';
        break;
      case 'referrals':
        toState = 'referrals-list';
        break;
      case 'appointments':
        toState = 'appointments-list';
        break;
      case 'transfers':
        toState = 'transferOfCare';
        break;
      case 'careplans':
        toState = 'eolcareplans';
        break;
      case 'mdt':
        toState = 'cancerMdt';
        break;
      case 'images':
        toState = 'images';
        break;
      case 'informationgov':
        toState = 'informationgov';
        break;
      }
      $state.go(toState, requestHeader);
    };

  });
