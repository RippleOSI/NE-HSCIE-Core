'use strict';

angular.module('rippleDemonstrator')
  .controller('NotificationsCtrl', function ($scope, $rootScope, Notifications) {

    $scope.acknowledge = function(source) {
      Notifications.acknowledge(source).then(function() {
        for(var i=0; i < $scope.notifications.length; i++) {
          if (source === $scope.notifications[i].source) {
            $scope.notifications.splice(i,1);
            $scope.apply();
          }
        }
      });
    };

    $scope.updateNotifications = function(notifications) {
      $scope.notifications = notifications.data;

      for(var i=0; i < $scope.notifications.length; i++) {
        $scope.notifications[i].timeCreated = moment($scope.notifications[i].timeCreated).fromNow();
      }
    };

    Notifications.all().then($scope.updateNotifications);


    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      Notifications.all().then($scope.updateNotifications);
    });
  });
