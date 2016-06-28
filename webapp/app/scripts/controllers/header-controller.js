'use strict';

angular.module('rippleDemonstrator')
  .controller('headerController', function ($scope, $rootScope, $state, $window, $modal, usSpinnerService, $stateParams, UserService, AdvancedSearch, Helper) {

    $rootScope.searchExpression = '';
    $scope.searchExpression = $rootScope.searchExpression;
    $scope.reportTypes = [];

    $scope.searchFocused = false;
    $rootScope.termsAcknowledged = false;

    var redirectUrl;

    // Get current user
    UserService.findCurrentUser().then( function (response) {
       redirectUrl = response.headers().location;

      if (redirectUrl) {
        $window.location = redirectUrl;
      }
      else {
        $rootScope.currentUser = response.data;

        $scope.searchBarEnabled = false;
        $scope.navBar = $rootScope.currentUser.feature.navBar;

        $modal.open({
          templateUrl: 'views/terms-and-conditions/terms-and-conditions-modal.html',
          size: 'lg',
          controller: 'TermsAndConditionsCtrl',
          resolve: {
            modal: function () {
              return {
                title: 'Terms and Conditions',
                heading: 'You must agree to the following terms and conditions before you proceed.'
              };
            }
          },
          backdrop: 'static',
          keyboard: false
        });

        // Direct different roles to different pages at login
        switch ($rootScope.currentUser.role) {
          case 'IDCR':
            $state.go('main-search');
            break;
          case 'PHR':
            $state.go('patients-summary', {
              patientId: $rootScope.currentUser.nhsNumber
            });
            break;
          default:
            $state.go('main-search');
            break;
        }
      }
    });

    $scope.logout = function () {
      UserService.logout().then(function (response) {
        redirectUrl = response.headers().location;

        if (redirectUrl) {
          $window.location = redirectUrl;
        }
      });
    };

    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      var params = $stateParams;
      var previousState = '';
      var pageHeader = '';
      var previousPage = '';

      var mainWidth = 0;
      var detailWidth = 0;

      switch (toState.name) {
        case 'main-search':
          previousState = '';
          pageHeader = 'Welcome';
          previousPage = '';
          mainWidth = 12;
          detailWidth = 0;
          break;
      case 'patients-list':
        previousState = 'patients-charts';
        pageHeader = 'Patient Lists';
        previousPage = 'Patient Dashboard';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-charts':
        previousState = '';
        pageHeader = 'Patient Dashboard';
        previousPage = '';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-search':
        previousState = '';
        pageHeader = 'Patient Dashboard';
        previousPage = '';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-landing':
        previousState = 'patients-search';
        pageHeader = 'Patient Summary';
        previousPage = 'Patient Search';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-summary':
        previousState = 'patients-list';
        pageHeader = 'Patient Summary';
        previousPage = 'Patient Lists';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-lookup':
        previousState = '';
        pageHeader = 'Patients lookup';
        previousPage = '';
        mainWidth = 6;
        detailWidth = 6;
        break;
      case 'search-report':
        previousState = 'patients-charts';
        pageHeader = 'Report Search';
        previousPage = 'Patient Dashboard';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'patients-list-full':
        previousState = 'patients-charts';
        pageHeader = 'Patients Details';
        previousPage = 'Patient Dashboard';
        mainWidth = 12;
        detailWidth = 0;
        break;
      case 'audits-by-patient':
        previousState = '';
        pageHeader = 'Admin Console';
        previousPage = '';
        mainWidth = 6;
        detailWidth = 6;
        break;
      case 'audits-by-user':
          previousState = '';
          pageHeader = 'Admin Console';
          previousPage = '';
          mainWidth = 6;
          detailWidth = 6;
          break;
      case 'admin-console':
        previousState = '';
        pageHeader = 'Admin Console';
        previousPage = '';
        mainWidth = 6;
        detailWidth = 6;
        break;
      default:
        previousState = 'patients-list';
        pageHeader = 'Patients Details';
        previousPage = 'Patient Lists';
        mainWidth = 6;
        detailWidth = 6;
        break;
      }

      $scope.checkExpression = function () {
        if (!Helper.containsKeyCode(event.keyCode) && $scope.searchExpression.length > 0 && /^(?!.*[0-9])/.test($scope.searchExpression)) {
          AdvancedSearch.openAdvancedSearch($scope.searchExpression);
        }
      };

      $scope.cancelSearchMode = function () {
        $rootScope.searchExpression = '';
        $scope.searchExpression = $rootScope.searchExpression;
      };

      $scope.searchFunction = function () {
        if ($scope.searchExpression.length > 0 && !isNaN($scope.searchExpression)) {
          AdvancedSearch.searchByDetails({nhsNumber: $scope.searchExpression}).then(function (result) {
            $scope.searchExpression = '';
            $scope.patients = result.data;
            changeState();
          });
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

      if (typeof $stateParams.ageFrom === 'undefined') {
        previousState = 'patients-list';
        previousPage = 'Patient Lists';
      }

      $scope.pageHeader = pageHeader;
      $scope.previousState = previousState;
      $scope.previousPage = previousPage;

      $scope.backButtonEnabled = $scope.currentUser.feature.homeView !== 'main-search';

      $scope.mainWidth = mainWidth;
      $scope.detailWidth = detailWidth;

      $scope.searchBarEnabled = !$state.is('main-search');

      $scope.goBack = function () {
        history.back();
      };

      $scope.footer = 'Integrated Digital Care Record';
      $scope.userContextViewExists = ('user-context' in $state.current.views);
      $scope.actionsExists = ('actions' in $state.current.views);

      $scope.go = function (patient) {
        $state.go('patients-summary', {
          patientId: patient.id
        });
      };

      if ($scope.currentUser.role === 'IDCR' || $scope.currentUser.role === 'IG') {
        $scope.title = 'HSCIE Ripple'
      }
      if ($scope.currentUser.role === 'PHR') {
        $scope.title = 'PHR POC'
      }

      $scope.goHome = function () {
        $scope.cancelSearchMode();

        if ($scope.currentUser.role === 'IDCR' || $scope.currentUser.role === 'IG') {
          $state.go('main-search');
        }
        else if ($scope.currentUser.role === 'PHR') {
          $state.go('patients-summary', {
            patientId: $scope.currentUser.nhsNumber
          });
        }
      };

      $scope.goToAdminConsole = function() {
        $state.go('admin-console');
      };
    });

    $scope.openAdvancedSearch = AdvancedSearch.openAdvancedSearch;

    $scope.$on("toggleHeaderSearchEnabled", function(event, enabled) {
      $scope.searchBarEnabled = enabled;
    });
  });
