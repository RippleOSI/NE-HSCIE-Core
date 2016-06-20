'use strict';

angular.module('rippleDemonstrator')
  .factory('UserService', function ($http, claims, content) {

    var currentUser = {
      role: claims.role,
      email: claims.email,
      tenant: {
        id: claims.tenant_id,
        name: claims.tenant_name
      },
      firstName: claims.given_name,
      surname: claims.family_name,
      username: claims.username,
      organisation: claims.organisation,
      permissions: 
      isAuthenticated: true,
      feature: claims.scope
    };

    var getCurrentUser = function () {
       return currentUser;
    };

    var getContent = function (key) {
      return content[key];
    };

    return {
      getCurrentUser: getCurrentUser,
      getContent: getContent
    };

  });
