'use strict';

define(['app/manager/module'], function (wbManager) {

    wbManager.service('managerService', managerService);

    function managerService($http) {

        // expose promises
        this.endpoint = function (name) {
            return function () {
                return $http.get('/manage/' + name);
            }
        };
    }

    wbManager.controller("ManagerTabCtrl", ManagerTabCtrl);

    ManagerTabCtrl.$inject = ['$scope', 'managerService', 'wbAlertService'];

    function ManagerTabCtrl($scope, managerService, alertService) {

        var vm = this;

        vm.tabs = [
            {
                title: 'Health',
                icon: 'fa-stethoscope',
                flatten: true,
                dataPromise: managerService.endpoint('health')
            },
            {
                title: 'Metrics',
                icon: 'fa-tachometer',
                flatten: false,
                dataPromise: managerService.endpoint('metrics')
            },
            {
                title: 'Environment',
                icon: 'fa-globe',
                flatten: false,
                dataPromise: managerService.endpoint('env')
            },
            {
                title: 'Info',
                icon: 'fa-info',
                flatten: false,
                dataPromise: managerService.endpoint('info')
            },
            {
                title: 'Auto config',
                icon: 'fa-automobile',
                flatten: false,
                dataPromise: managerService.endpoint('autoconfig')
            },
            {
                title: 'Config properties',
                icon: 'fa-edit',
                flatten: false,
                dataPromise: managerService.endpoint('configprops')
            },
            {
                title: 'Beans',
                icon: 'fa-code',
                flatten: false,
                dataPromise: managerService.endpoint('beans')
            },

            {
                title: 'Thread dump',
                icon: 'fa-trash',
                flatten: false,
                dataPromise: managerService.endpoint('dump')
            },
            {
                title: 'Trace',
                icon: 'fa-binoculars',
                flatten: false,
                dataPromise: managerService.endpoint('trace')
            }
        ];

        vm.selected = function (tab) {
            vm.tabs.forEach(function (entry) {
                entry.actve = entry === tab;
            });
            this.selectedTab = tab;
            log("Tab", tab);
            reloadTab(tab);

        };

        vm.closeAlert = function (alert) {
            alertService.closeAlert(alert);
        };

        function reloadTab(tab) {
            tab.dataPromise().then(function (response) {
                tab.data = response.data;
            }, function (response) {
                log("Got response", response.data);
                alertService.add("warning", response.data, 10000);
            });
        }

    }

});

