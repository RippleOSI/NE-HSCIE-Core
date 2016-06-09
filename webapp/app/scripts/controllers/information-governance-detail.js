'use strict';

angular.module('rippleDemonstrator')
  .controller('InformationGovernanceDetailCtrl', function ($scope, $stateParams, SearchInput, $modal, $state, $location, Helper, usSpinnerService, UserService, PatientService, InformationGovernance) {

    SearchInput.update();

    var currentUser = UserService.getCurrentUser();
    $stateParams.patientSource = currentUser.feature.patientSource;

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;
    });

    InformationGovernance.get($stateParams.patientId, $stateParams.consentId).then(function (result) {
      $scope.consent = result.data;
      usSpinnerService.stop('info-gov-spinner');
    });

    $scope.edit = function () {
      var modalInstance = $modal.open({
        templateUrl: 'views/information-governance/information-governance-modal.html',
        size: 'lg',
        controller: 'InformationGovernanceModalCtrl',
        resolve: {
          modal: function () {
            return {
              title: 'Edit Information Governance'
            };
          },
          consent: function () {
            return angular.copy($scope.consent);
          },
          patient: function () {
            return $scope.patient;
          }
        }
      });

      modalInstance.result.then(function (consent) {
        var toUpdate = {
          sourceId: consent.sourceId,
          optIn: consent.optIn,
          reason: consent.reason,
          author: consent.author,
          dateCreated: consent.dateCreated
        };

        InformationGovernance.update($scope.patient.nhsNumber, toUpdate).then(function () {
          setTimeout(function () {
            $state.go('informationgov-detail', {
              patientId: $scope.patient.nhsNumber,
              consentIndex: consent.sourceId,
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
