
define(['angular', 'restangular', 'angular_loading_bar', './common/index', './editor/index', './manager/index'], function (angular) {
    'use strict';

    return angular.module('wb', ['angular-loading-bar', 'wb.common', 'wb.editor', 'wb.manager'])
        .config(['RestangularProvider', function (RestangularProvider) {
            RestangularProvider.setBaseUrl("/api");
        }])
        .run(['$window', function ($window) {

            $window.log = function () {
                log.history = log.history || [];   // store logs to an array for reference
                log.history.push(arguments);
                if (console) {
                    console.log(Array.prototype.slice.call(arguments));
                }
            };

            $window.log("Starting app ...");
        }]);

});



