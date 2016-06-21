'use strict';

angular.module('rippleDemonstrator')
  .controller('AdminCtrl', function ($scope, SearchInput, UserService) {

    SearchInput.update();

    UserService.findCurrentUser().then(function (response) {
        $scope.adminUser = response.data;
    });

  });
