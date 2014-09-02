'use strict';

define(['lodash', 'app/editor/module'], function (_, module) {

    return module.directive('wbCodeEditor', function () {
        return {
            restrict: 'EA',
            replace: true,
            controller: CodeEditorCtrl,
            controllerAs: 'editor',
            templateUrl: 'app/editor/wbCodeEditor.html',
            scope: { pane: '=pane' }
        }
    });

    function CodeEditorCtrl($scope) {

        var vm = this;

        vm.pane = $scope.pane;

        vm.state = {
            changed: false,
            readonly: false
        };

        vm.aceLoaded = aceLoaded;
        vm.save = save;
        vm.aceChanged = aceChanged;

        vm.aceBlur = aceBlur;

        var throttledSave = _.throttle(vm.save, 10000, {
            leading: false,
            trailing: true
        });

        function aceLoaded(editor) {
            var session = editor.getSession();
            session.setUndoManager(new ace.UndoManager());
            editor.setShowPrintMargin(false);
        }

        function aceChanged(e) {
            vm.state.changed = true;
            throttledSave();
        }

        function aceBlur(e) {
            // vm.save();

        }

        function save() {

            log("Saving ...");

            // todo clean up this
            if (vm.pane.title === "Template") {
                vm.pane.data.put();
            }
            else {
                Restangular.one("article", vm.pane.data.id).customPUT(vm.pane.data);
            }
            vm.state.changed = false;
        }


        vm.toggleReadOnly = function () {
            vm.state.readonly = !vm.state.readonly;
        };
    }


});