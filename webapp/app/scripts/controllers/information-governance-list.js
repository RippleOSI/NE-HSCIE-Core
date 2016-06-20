'use strict';

angular.module('rippleDemonstrator')
  .controller('InformationGovernanceCtrl', function ($scope, $location, $stateParams, SearchInput, $modal, $state, $filter, usSpinnerService, UserService, PatientService, InformationGovernance) {

    $scope.currentPage = 1;
    SearchInput.update();

    UserService.findCurrentUser().then(function (response) {
      $scope.currentUser = response.data;
      $stateParams.patientSource = $scope.currentUser.feature.patientSource;
    });

    $scope.pageChangeHandler = function (newPage) {
      $scope.currentPage = newPage;
    };

    $scope.search = function (row) {
      return (
        angular.lowercase($filter('optedIn')(row.optIn)).indexOf(angular.lowercase($scope.query) || '') !== -1
      );
    };

    // State params
    if ($stateParams.page) {
      $scope.currentPage = $stateParams.page;
    }

    if ($stateParams.filter) {
      $scope.query = $stateParams.filter;
    }

    // HTTP services
    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    InformationGovernance.all($stateParams.patientId).then(function (result) {
      $scope.consents = result.data;

      for (var i = 0; i < $scope.consents.length; i++) {
        $scope.consents[i].dateCreated = new Date($scope.consents[i].dateCreated).toISOString();
      }

      usSpinnerService.stop('info-gov-spinner');
    });

    $scope.go = function (id) {
      $state.go('informationgov-detail', {
        patientId: $scope.patient.nhsNumber,
        consentId: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType
      });
    };

    $scope.selected = function ($index) {
      return $index === $stateParams.consentId;
    };

    $scope.create = function () {
      var modalInstance = $modal.open({
        templateUrl: 'views/information-governance/information-governance-modal.html',
        size: 'lg',
        controller: 'InformationGovernanceModalCtrl',
        resolve: {
          modal: function () {
            return {
              title: 'Create Information Governance'
            };
          },
          consent: function () {
            return {
              optIn: !$scope.patient.optIn
            }
          },
          patient: function () {
            return $scope.patient;
          }
        }
      });

      modalInstance.result.then(function (consent) {
        var toAdd = {
          sourceId: consent.sourceId,
          optIn: consent.optIn,
          reason: consent.reason,
          author: consent.author,
          dateCreated: consent.dateCreated
        };

        InformationGovernance.create($scope.patient.nhsNumber, toAdd).then(function () {
          setTimeout(function () {
            $state.go('informationgov', {
              patientId: $scope.patient.nhsNumber,
              filter: $scope.query,
              page: $scope.currentPage,
              reportType: $stateParams.reportType,
              searchString: $stateParams.searchString,
              queryType: $stateParams.queryType
            }, {
              reload: true
            });
          }, 2000);
        });
      });
    };

  });
