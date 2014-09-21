define(['app/common/module'], function (module) {

    'use strict';

    return module.factory('wbAlertService', ['$rootScope', '$timeout', function ($rootScope, $timeout) {
        var alertService;
        $rootScope.alerts = [];

        return alertService = {
            add: function (type, msg, timeout) {
                $rootScope.alerts.push({
                    type: type,
                    msg: msg,
                    close: function () {
                        return alertService.closeAlert(this);
                    }
                });

                if (timeout) {
                    $timeout(function () {
                        alertService.closeAlert(this);
                    }, timeout);
                }
            },
            closeAlert: function (alert) {
                return this.closeAlertIdx($rootScope.alerts.indexOf(alert));
            },
            closeAlertIdx: function (index) {
                return $rootScope.alerts.splice(index, 1);
            }

        };

    }]);

});
