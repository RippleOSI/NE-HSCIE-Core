'use strict';

angular.module('rippleDemonstrator')
  .controller('InformationGovernanceDetailCtrl', function ($scope, $stateParams, SearchInput, $modal, $state, $location, Helper, usSpinnerService, PatientService, InformationGovernance) {

    SearchInput.update();
    PatientService.get($stateParams.patientId).then(function (patient) {
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

        InformationGovernance.update($scope.patient.id, toUpdate).then(function () {
          setTimeout(function () {
            $state.go('informationgov-detail', {
              patientId: $scope.patient.id,
              consentIndex: Helper.updateId(consent.sourceId),
              page: $scope.currentPage,
              reportType: $stateParams.reportType,
              searchString: $stateParams.searchString,
              queryType: $stateParams.queryType
            });
          }, 2000);
        });
      });
    };

  });
