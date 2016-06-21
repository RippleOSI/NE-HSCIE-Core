'use strict';

angular
  .module('rippleDemonstrator', [
    'ngResource',
    'ngTouch',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'growlNotifications',
    'angularUtils.directives.dirPagination',
    'ui.timepicker',
    'ui.calendar',
    'angularSpinner',
    'mgcrea.ngStrap.popover'
  ])
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('patients-list', {
        url: '/patients?ageRange&department&order&reverse',
        views: {
          main: { templateUrl: 'views/patients/patients-list.html', controller: 'PatientsListCtrl' }
        }
      })

      .state('patients-list-full', {
        url: '/patients-full-details?ageFrom&ageTo&orderType&pageNumber&reportType&searchString&queryType',
        views: {
          main: { templateUrl: 'views/search/patients-list-full.html', controller: 'PatientsListFullCtrl' }
        }
      })

      .state('patients-charts', {
         url: '/charts',
        views: {
          main: { templateUrl: 'views/patients/patients-charts.html', controller: 'PatientsChartsCtrl' }
        }
      })

      .state('patients-landing', {
        url: '/patients/{patientId:int}/patients-landing?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/patients/patients-landing.html', controller: 'PatientsLandingCtrl' }
        }
      })

      .state('main-search', {
        url: '/search',
        views: {
          main: { templateUrl: 'views/main-search/main-search.html', controller: 'MainSearchController' }
        }
      })

      .state('search-report', {
         url: '/search-report?searchString',
        views: {
          main: { templateUrl: 'views/search/report-chart.html', controller: 'ReportChartsCtrl' }
        }
      })

      .state('patients-lookup', {
        url: '/lookup',
        views: {
          actions: { templateUrl: 'views/home-sidebar.html' },
          main: { controller: 'PatientsLookupCtrl' }
        }
      })

      .state('patients-summary', {
        url: '/patients/{patientId:int}/patients-summary?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/patients/patients-summary.html', controller: 'PatientsSummaryCtrl' }
        }
      })

      .state('keyworkers-list', {
        url: '/patients/{patientId:int}/keyworkers?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/keyworkers/keyworkers-list.html', controller: 'KeyworkersListCtrl' }
        }
      })

      .state('keyworkers-detail', {
        url: '/patients/{patientId:int}/keyworkers/{keyworkerIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/keyworkers/keyworkers-list.html', controller: 'KeyworkersListCtrl' },
          detail: { templateUrl: 'views/keyworkers/keyworkers-detail.html', controller: 'KeyworkersDetailCtrl' }
        }
      })



      .state('allergies', {
        url: '/patients/{patientId:int}/allergies?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/allergies/allergies-list.html', controller: 'AllergiesListCtrl' }
        }
      })

      .state('allergies-detail', {
        url: '/patients/{patientId:int}/allergies/{allergyIndex}?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/allergies/allergies-list.html', controller: 'AllergiesListCtrl' },
          detail: { templateUrl: 'views/allergies/allergies-detail.html', controller: 'AllergiesDetailCtrl' }
        }
      })


	  .state('medications', {
        url: '/patients/{patientId:int}/hscie-medications?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/medications/medications-list.html', controller: 'MedicationsListCtrl' }
        }
      })

      .state('medications-detail', {
        url: '/patients/{patientId:int}/hscie-medications/{medicationIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/medications/medications-list.html', controller: 'MedicationsListCtrl' },
          detail: { templateUrl: 'views/medications/medications-detail.html', controller: 'MedicationsDetailCtrl' }
        }
      })
      .state('contacts-list', {
        url: '/patients/{patientId:int}/contacts?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/contacts/contacts-list.html', controller: 'ContactsListCtrl' }
        }
      })

      .state('contacts-detail', {
        url: '/patients/{patientId:int}/contacts/{contactIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/contacts/contacts-list.html', controller: 'ContactsListCtrl' },
          detail: { templateUrl: 'views/contacts/contacts-detail.html', controller: 'ContactsDetailCtrl' }
        }
      })

      .state('problems-list', {
        url: '/patients/{patientId:int}/problems?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/problems/problems-list.html', controller: 'ProblemsListCtrl' }
        }
      })

      .state('problems-detail', {
        url: '/patients/{patientId:int}/problems/{problemIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/problems/problems-list.html', controller: 'ProblemsListCtrl' },
          detail: { templateUrl: 'views/problems/problems-detail.html', controller: 'ProblemsDetailCtrl' }
        }
      })
      //.state('contacts', {
       // url: '/patients/{patientId:int}/contacts?reportType&searchString&queryType',
       // views: {
          //'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          //actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          //main: { templateUrl: 'views/contacts/contacts-list.html', controller: 'ContactsListCtrl' }
        //}
      //})

      //.state('contacts-detail', {
       // url: '/patients/{patientId:int}/contacts/{contactIndex}?filter&page&reportType&searchString&queryType',
        //views: {
          //'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          //actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          //main: { templateUrl: 'views/contacts/contacts-list.html', controller: 'ContactsListCtrl' },
          //detail: { templateUrl: 'views/contacts/contacts-detail.html', controller: 'ContactsDetailCtrl' }
       // }
     // })

      .state('transferOfCare', {
        url: '/patients/{patientId:int}/transfer?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/transfer-of-care/transfer-of-care-list.html', controller: 'TransferOfCareListCtrl' }
        }
      })

      .state('transferOfCare-detail', {
        url: '/patients/{patientId:int}/transfer/{transferIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/transfer-of-care/transfer-of-care-list.html', controller: 'TransferOfCareListCtrl' },
          detail: { templateUrl: 'views/transfer-of-care/transfer-of-care-detail.html', controller: 'TransferOfCareDetailCtrl' }
        }
      })


      .state('transferOfCare-create', {
        url: '/patients/{patientId:int}/transfer-of-care-create?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { controller: 'TransferOfCareCtrl' }
        }
      })

    .state('cancerMdt', {
      url: '/patients/{patientId:int}/cancer-mdt-list?reportType&searchString&queryType',
      views: {
        'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
        actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
        main: { templateUrl: 'views/cancer-mdt/cancermdt-list.html', controller: 'CancerMdtListCtrl' }
      }
    })

      .state('cancerMdt-detail', {
        url: '/patients/{patientId:int}/cancer-mdt-detail/{cancerMdtIndex}?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/cancer-mdt/cancermdt-list.html', controller: 'CancerMdtListCtrl' },
          detail: { templateUrl: 'views/cancer-mdt/cancer-mdt-detail.html', controller: 'CancerMdtDetailCtrl' }
        }
      })

     .state('procedures', {
        url: '/patients/{patientId:int}/procedures?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/procedures/procedures-list.html', controller: 'ProceduresListCtrl' }
        }
      })

      .state('procedures-detail', {
        url: '/patients/{patientId:int}/procedures/{procedureId}?filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/procedures/procedures-list.html', controller: 'ProceduresListCtrl' },
          detail: { templateUrl: 'views/procedures/procedures-detail.html', controller: 'ProceduresDetailCtrl' }
        }
      })

      .state('referrals-list', {
        url: '/patients/{patientId:int}/referrals?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/referrals/referrals-list.html', controller: 'ReferralListCtrl' }
        }
      })

      .state('referrals-detail', {
        url: '/patients/{patientId:int}/referrals/{referralIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/referrals/referrals-list.html', controller: 'ReferralListCtrl' },
          detail: { templateUrl: 'views/referrals/referrals-detail.html', controller: 'ReferralDetailCtrl' }
        }
      })


    .state('eolcareplans', {
        url: '/patients/{patientId:int}/eolcareplans?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/care-plans/eolcareplans-list.html', controller: 'EolcareplansListCtrl' }
        }
      })

      .state('eolcareplans-detail', {
        url: '/patients/{patientId:int}/eolcareplans/{eolcareplansIndex}?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/care-plans/eolcareplans-list.html', controller: 'EolcareplansListCtrl' },
          detail: { templateUrl: 'views/care-plans/eolcareplans-detail.html', controller: 'EolcareplansDetailCtrl' }
        }
      })

     .state('appointments-list', {
        url: '/patients/{patientId:int}/appointments?patientSource&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/appointments/appointments-list.html', controller: 'AppointmentListCtrl' }
        }
      })

      .state('appointments-detail', {
        url: '/patients/{patientId:int}/appointments/{appointmentIndex}?patientSource&filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/appointments/appointments-list.html', controller: 'AppointmentListCtrl' },
          detail: { templateUrl: 'views/appointments/appointments-detail.html', controller: 'AppointmentDetailCtrl' }
        }
      })

      .state('orders', {
        url: '/patients/{patientId:int}/orders?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/orders/orders-list.html', controller: 'OrdersListCtrl' }
        }
      })

      .state('orders-detail', {
        url: '/patients/{patientId:int}/orders/{orderId}?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/orders/orders-list.html', controller: 'OrdersListCtrl' },
          detail: { templateUrl: 'views/orders/orders-detail.html', controller: 'OrdersDetailCtrl' }
        }
      })

       .state('results', {
        url: '/patients/{patientId:int}/results?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/results/results-list.html', controller: 'ResultsListCtrl' }
        }
      })

      .state('informationgov', {
        url: '/patients/{patientId:int}/consents?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/information-governance/information-governance-list.html', controller: 'InformationGovernanceCtrl' }
        }
      })

      .state('informationgov-detail', {
        url: '/patients/{patientId:int}/consents/{consentId}?reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/information-governance/information-governance-list.html', controller: 'InformationGovernanceCtrl' },
          detail: { templateUrl: 'views/information-governance/information-governance-detail.html', controller: 'InformationGovernanceDetailCtrl' }
        }
      })

      .state('results-detail', {
        url: '/patients/{patientId:int}/results/{resultIndex}?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/results/results-list.html', controller: 'ResultsListCtrl' },
          detail: { templateUrl: 'views/results/results-detail.html', controller: 'ResultsDetailCtrl' }
        }
      })

      .state('images', {
        url: '/patients/{patientId:int}/images?filter&page&reportType&searchString&queryType',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/dicom/image-list.html', controller: 'ImageListCtrl' }
        }
      })

      .state('images-detail', {
        url: '/patients/{patientId:int}/images/{studyId}?filter&page&reportType&searchString&queryType&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/dicom/image-list.html', controller: 'ImageListCtrl' },
          detail: { templateUrl: 'views/dicom/image-detail.html', controller: 'ImageDetailCtrl' }
        }
      })

      .state('admin-console', {
        url: '/admin',
        views: {
          'user-context': { templateUrl: 'views/admin/admin-context.html', controller: 'AdminCtrl' },
          actions: { templateUrl: 'views/admin/admin-sidebar.html', controller: 'AdminCtrl' }
        }
      })

      .state('audits-by-patient', {
        url: '/audits/patient',
        views: {
        	'user-context': { templateUrl: 'views/admin/admin-context.html', controller: 'AdminCtrl' },
          actions: { templateUrl: 'views/admin/admin-sidebar.html', controller: 'AdminCtrl' },
          main: { templateUrl: 'views/audits/audit-list-by-patient.html', controller: 'AuditListCtrl' },
          detail: { templateUrl: 'views/audits/audit-detail.html', controller: 'AuditDetailCtrl' }
        }
      })

      .state('audits-by-user', {
        url: '/audits/user',
        views: {
        	'user-context': { templateUrl: 'views/admin/admin-context.html', controller: 'AdminCtrl' },
          actions: { templateUrl: 'views/admin/admin-sidebar.html', controller: 'AdminCtrl' },
          main: { templateUrl: 'views/audits/audit-list-by-user.html', controller: 'AuditListCtrl' },
          detail: { templateUrl: 'views/audits/audit-detail.html', controller: 'AuditDetailCtrl' }
        }
      })

      .state('alerts', {
        url: '/patients/{patientId:int}/alerts?filter&page',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/alerts/alerts-list.html', controller: 'AlertsListCtrl' }
        }
      })

      .state('alerts-detail', {
        url: '/patients/{patientId:int}/alerts/{alertId}?filter&page&source',
        views: {
          'user-context': { templateUrl: 'views/patients/patients-context.html', controller: 'PatientsDetailCtrl' },
          actions: { templateUrl: 'views/patients/patients-sidebar.html', controller: 'PatientsDetailCtrl' },
          main: { templateUrl: 'views/alerts/alerts-list.html', controller: 'AlertsListCtrl' },
          detail: { templateUrl: 'views/alerts/alerts-detail.html', controller: 'AlertsDetailCtrl' }
        }
      });
  })

  .directive('datepickerPopup', function () {
    return {
      restrict: 'EAC',
      require: 'ngModel',
      link: function (scope, element, attr, controller) {
        controller.$formatters.unshift();
      }
    };
  })

  .directive('mySpace', function () {
    return function (scope, element, attrs) {
      element.bind('keydown keypress', function (event) {
        if (event.which === 32) {
          scope.$apply(function () {
            scope.$eval(attrs.myEnter);
          });

          event.preventDefault();
        }
      });
    };
  })

  .directive('autoFocus', function($timeout) {
    return {
      restrict: 'A',
      require: 'ngModel',
      scope : {
        ngModel: '='
      },
      link: function(scope, elem, attrs, ctrl) {
        scope.$watch("ngModel", function (value) {
          $timeout(function () {
            elem[0].focus();
          });
        });
      }
    };
  })

  .directive('isValidNhsNumber', function() {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, elem, attrs, ctrl) {
        scope.$watch(attrs.ngModel, function(value) {
          checkFormat(value);
        });

        ctrl.$parsers.unshift(function(value) {
          return checkFormat(value);
        });

        var checkFormat = function(value) {
          // Strip white space
          if(value) {
            var nhsNum = value.replace(/\s+/g, '');
            var valid = !isNaN(nhsNum) && nhsNum.length === 10;

            ctrl.$setValidity('invalidNHSNumFormat', valid);

            return valid ? nhsNum : '';
          }
        }
      }
    }
  })

  .constant('keyCodes', {
    esc: 27,
    enter: 13
  })

  .directive('keyBind', ['keyCodes', function (keyCodes) {
    function map(obj) {
      var mapped = {};
      for (var key in obj) {
        var action = obj[key];
        if (keyCodes.hasOwnProperty(key)) {
          mapped[keyCodes[key]] = action;
        }
      }
      return mapped;
    }

    return function (scope, element, attrs) {
      var bindings = map(scope.$eval(attrs.keyBind));
      element.bind('keydown keypress', function (event) {
        if (bindings.hasOwnProperty(event.which)) {
          scope.$apply(function () {
            scope.$eval(bindings[event.which]);
          });
        }
      });
    };
  }])

  .directive('focusElement', function($timeout) {
    return {
      link: function(scope, element, attrs) {
        scope.$watch(attrs.focusElement, function(value) {
          $timeout(function() {
            if(value === true) {
              element.focus();
            }
          });
        });
      }
    }
  })

  .directive('rpOnLoad', ['$parse', function ($parse) {
    return function (scope, elem, attrs) {
      var fn = $parse(attrs.rpOnLoad);

      elem.on('load', function (event) {
        scope.$apply(function () {
          fn(scope, {$event: event});
        });
      });
    };
  }])

  .directive('customPopover', function ($templateCache) {
    return {
      restrict: 'A',
      template: '<span>{{label}}</span>',
      link: function(scope, elem, attrs) {
        scope.label = attrs.popoverLabel;
        var template = $templateCache.get("example.html");

        elem.bind('click', function(e){
          $(elem).popover({
            html: true,
            title: "Notifications (" + scope.notifications.length + ")",
            content: template,
            placement: attrs.popoverPlacement,
            dataContent: scope.notifications
          });
        });
      }
    };
  })

  .filter('optedIn', function() {
    return function(optedIn) {
        return optedIn ? 'Opted In' : 'Opted Out';
    }
  })

  .config(function (datepickerConfig, datepickerPopupConfig, cfpLoadingBarProvider) {
    datepickerConfig.startingDay = 1;
    datepickerPopupConfig.showButtonBar = false;
    datepickerPopupConfig.datepickerPopup = 'dd-MMM-yyyy';
    cfpLoadingBarProvider.includeSpinner = false;
  })

  .config(function (paginationTemplateProvider) {
    paginationTemplateProvider.setPath('views/dirPagination.tpl.html');
  });
