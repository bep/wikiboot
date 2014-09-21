define(['app/editor/module'], function (module) {

    'use strict';

    return module.directive('wbPreview', function () {
        return {
            restrict: 'EA',
            replace: true,
            templateUrl: 'app/editor/wbPreview.html',
            scope: { pane: '=pane' }
        }
    });


});
