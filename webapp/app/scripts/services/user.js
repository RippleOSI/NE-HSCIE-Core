'use strict';

angular.module('rippleDemonstrator')
  .factory('UserService', function ($rootScope, $http, claims, content) {

    var findCurrentUser = function () {
       return $http.get('/api/user').then(function (response) {
         var currentUser = response.data;

         if (currentUser && currentUser !== '') {
           currentUser.tenant = {
             id: response.data.tenant,
             name: claims.tenant_name
           };

           currentUser.isAuthenticated = true;
           currentUser.organisation = claims.organisation;
           currentUser.feature = claims.scope;
           currentUser.hasPermission = function(role) {
             return $rootScope.currentUser.permissions.indexOf(role) > -1;
           };

           response.data = currentUser;
         }

         return response;
       });
    };

    var getContent = function (key) {
      return content[key];
    };

    var logout = function () {
      return $http.get('/api/logout');
    };

    return {
      findCurrentUser: findCurrentUser,
      getContent: getContent,
      logout: logout
    };

  });
