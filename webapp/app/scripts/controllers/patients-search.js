'use strict';

angular.module('rippleDemonstrator')
        .controller('PatientsSearchCtrl', function ($scope, $window, $state, PatientSearchService, $modal) {

            $scope.searching = false;

            $('#dateofbirth').datepicker({dateFormat: 'dd-MMM-y'});

            $scope.dateofBirthDatepicker = function ($event, name) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope[name] = true;
            };

            $scope.searchPatients = function (search) {
                $scope.searching = true;

                PatientSearchService.searchPatients(search).then(function (result) {
                    $scope.patients = result;
                    $scope.searching = false;
                }, function () {
                    $scope.searching = false;
                });
            };

            $scope.sort = function () {
                if ($stateParams.orderType === 'ASC') {
                    $stateParams.orderType = 'DESC';
                } else {
                    $stateParams.orderType = 'ASC';
                }
                $stateParams.pageNumber = 1;
                getData();
            };

        });