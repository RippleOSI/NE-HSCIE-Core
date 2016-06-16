'use strict';

angular.module('rippleDemonstrator')
  .controller('AdminCtrl', function ($scope, SearchInput, UserService) {

    SearchInput.update();

    $scope.adminUser = UserService.getCurrentUser();

  });
