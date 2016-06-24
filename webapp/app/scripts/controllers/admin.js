'use strict';

angular.module('rippleDemonstrator')
  .controller('AdminCtrl', function ($scope, $state, SearchInput, UserService) {

    SearchInput.update();

    UserService.findCurrentUser().then(function (response) {
        $scope.adminUser = response.data;
    });

    $scope.goTo = function (section) {
      var toState = '';

      switch (section) {
        case 'adminConsole':
          toState = 'admin-console';
          break;
      }

      $state.go(toState);
    }
  });
