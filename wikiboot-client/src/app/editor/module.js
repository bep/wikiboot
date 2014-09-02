define(['angular', 'lodash', 'restangular', 'angularsanitize', 'angular_bootstrap', 'ace', 'angular_ui_ace'], function (ng) {
    'use strict';

    return ng.module('wb.editor', ['ui.ace', 'ui.bootstrap', 'restangular', 'ngSanitize']);
});