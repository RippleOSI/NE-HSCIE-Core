'use strict';

angular.module('rippleDemonstrator')
  .controller('PatientsSummaryCtrl', function ($scope, $stateParams, $state, SearchInput, $rootScope, $location, usSpinnerService, PatientService, UserService) {

    SearchInput.update();
    $scope.patients = $stateParams.patientsList;

    UserService.findCurrentUser().then(function (response) {
      $scope.currentUser = response.data;
      $stateParams.patientSource = $scope.currentUser.feature.patientSource;
    });

    PatientService.get($stateParams.patientId, $stateParams.patientSource).then(function (patient) {
      $scope.patient = patient;

      //$scope.allergiesCount = patient.allergies.length;
      //$scope.allergies = patient.allergies.slice(0, 5);

      $scope.medicationsCount = patient.medications.length;
      $scope.medications = patient.medications.slice(0, 5);

	    $scope.diagnosesCount = patient.problems.length;
      $scope.diagnoses = patient.problems.slice(0, 5);

      $scope.contactsCount = patient.contacts.length;
      $scope.contacts = patient.contacts.slice(0, 20);

      $scope.transferofCaresCount = patient.transfers.length;
      $scope.transferofCareComposition = patient;

      var descendingTransferofCareComposition = [];
      for (var x = $scope.transferofCareComposition.transfers.length - 1; x >= 0; x--) {
        descendingTransferofCareComposition.push($scope.transferofCareComposition.transfers[x]);
      }

      $scope.transferofCareComposition.transfers = descendingTransferofCareComposition;
      $scope.transferofCareComposition = $scope.transferofCareComposition.transfers.slice(0, 5);

      $scope.isOptedOut = !$scope.patient.optIn;

      usSpinnerService.stop('patientSummary-spinner');
    });

    $scope.go = function (path) {
      $location.path(path);
    };

    $scope.goToSection = function (section) {
      var requestHeader = {
        patientId: $stateParams.patientId,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        patientSource: $stateParams.patientSource
      };

      var toState = '';
      switch (section) {
      case 'Problems':
        toState = 'problems-list';
        break;
      case 'Allergies':
        toState = 'allergies';
        break;
      case 'Medications':
        toState = 'medications';
        break;
      case 'Keyworkers':
        toState = 'keyworkers-list';
        break;
      case 'Transfer':
        toState = 'transferOfCare';
        break;
      }
      $state.go(toState, requestHeader);
    };

    $scope.goKeyworker = function (id, keyworkerSource) {
      $state.go('keyworkers-detail', {
        patientId: $scope.patient.nhsNumber,
        keyworkerIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: keyworkerSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.goTransfer = function (id, transferSource) {
      $state.go('transferOfCare-detail', {
        patientId: $scope.patient.nhsNumber,
        transferIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: transferSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.goProblem = function (id, problemSource) {
      $state.go('problems-detail', {
        patientId: $scope.patient.nhsNumber,
        problemIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: problemSource,
        patientSource: $stateParams.patientSource
      });
    };

    $scope.goMedication = function (id, medicationSource) {
      $state.go('medications-detail', {
        patientId: $scope.patient.nhsNumber,
        medicationIndex: id,
        filter: $scope.query,
        page: $scope.currentPage,
        reportType: $stateParams.reportType,
        searchString: $stateParams.searchString,
        queryType: $stateParams.queryType,
        source: medicationSource,
        patientSource: $stateParams.patientSource
      });
    };

    var admCount = 0;
    var outCount = 0;
    var latestResult = false;
    var savedId = "";
    var savedText = "";
    var savedSource = "";

    $scope.countKeyContact = function (contact, contacts, index) {

      if (contact.sourceId.indexOf("- Key Contact") > -1) {
        $scope.contacts.splice(1, 1);
        return false;

        if (contact.sourceId.indexOf("ADMISSION - Key Contact") > -1) {
          admCount++;
        }
        else if (contact.sourceId.indexOf("DISCHARGES - Key Contact") > -1) {
          outCount++;
        }

        if (admCount != outCount) {
          if (contact.sourceId.indexOf("OUTPATIENTS - Key Contact") > -1) {
            $scope.contacts.splice(1, 1);
            return false;
          }
        }
        if (latestResult == false) {
          latestResult = true;
          savedId = contact.sourceId;
          savedSource = contact.source;
          savedText = contact.text;
        } else if (contact.sourceId != savedId) {
          $scope.contacts.splice(1, 1);
          return false;
        }
      }

      $scope.contactsCount = $scope.contacts.length;
      return true;

    };

    $scope.hideKeyContact = function (contact) {
      return !contact.sourceId.indexOf("- Key Contact") > -1;
    };

    $scope.showKeyContact = function (contact) {
      return contact.sourceId.indexOf("- Key Contact") > -1;
    };

    $scope.displayKeyContact = function (contact) {
      return !contact.sourceId.indexOf("- Key Contact") > -1;
    };

  });
