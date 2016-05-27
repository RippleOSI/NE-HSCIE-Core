'use strict';

angular.module('rippleDemonstrator')
  .controller('InformationGovernanceCtrl', function ($scope, $location, $stateParams, SearchInput, $modal, $state, $filter, usSpinnerService, PatientService, InformationGovernance) {

    $scope.currentPage = 1;
    SearchInput.update();

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
    PatientService.get($stateParams.patientId).then(function (patient) {
      $scope.patient = patient;
    });

    InformationGovernance.all($stateParams.patientId).then(function (result) {
      $scope.consents = result.data;

      for (var i = 0; i < $scope.consents.length; i++) {
        $scope.consents[i].dateCreated = moment($scope.consents[i].dateCreated).format('DD-MMM-YYYY');
      }

      usSpinnerService.stop('info-gov-spinner');
    });

    $scope.go = function (id) {
      $state.go('informationgov-detail', {
        patientId: 9999999468, //$scope.patient.id,
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
              optIn: false //!$scope.patient.optedIn
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

        InformationGovernance.create($scope.patient.id, toAdd).then(function () {
          setTimeout(function () {
            $state.go('information-gov', {
              patientId: 9999999468, //$scope.patient.id,
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
