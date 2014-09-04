'use strict';

define(['utils', 'app/common/module'], function (utils, module) {

    return module.directive('wbJsonHuman', function () {
        return {
            restrict: 'EA',
            replace: true,
            controller: ManageViewerCtrl,
            templateUrl: 'app/common/directives/wbJsonHuman.html',
            scope: { options: '=options' }
        }
    });

    ManageViewerCtrl.$inject = ['$scope', '$element'];

    function ManageViewerCtrl($scope, $element) {

        $scope.$watch('options.data', function (newValue, oldValue) {
            if (newValue && (newValue !== oldValue)) {
                refreshPane();
            }
        });

        function refreshPane() {
            $element.children(1).replaceWith(utils.jsonToHtml($scope.options.data, $scope.options.flatten));
        }
    }

});
