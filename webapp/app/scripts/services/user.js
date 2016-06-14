'use strict';

angular.module('rippleDemonstrator')
  .factory('UserService', function ($http, claims, content) {

    var findCurrentUser = function () {
       return $http.get('/api/user').then(function (response) {
         var currentUser = response.data;

         currentUser.tenant = {
           id: response.data.tenant,
           name: claims.tenant_name
         };

         currentUser.isAuthenticated = true;
         currentUser.feature = claims.scope;

         response.data = currentUser;

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
