'use strict';

define(['app/editor/module'], function (module) {

    return module.directive('wbPreview', function () {
        return {
            restrict: 'EA',
            replace: true,
            templateUrl: 'app/editor/wbPreview.html',
            scope: { pane: '=pane' }
        }
    });


});