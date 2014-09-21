define(['app/editor/module'], function (wbEditor) {
    'use strict';

    wbEditor.service('articleService', articleService);

    articleService.$inject = ['Restangular'];

    function articleService(Restangular) {

        // data for the template tab
        var templateResource = Restangular.one("templates", 1);
        // render the template for a data item
        var renderResource = Restangular.one("rendering/render", 1);
        // Wikipedia preview
        var previewResource = Restangular.one("rendering/preview", 1);

        // Current dataSet
        var dataSetResource = Restangular.all("rendering/dataSet");

        var pages = {
            currentPage: 1,
            totalItems: 109
        };

        var dataSetIndex = function () {
            return pages.currentPage - 1;
        };

        // expose promises
        this.template = function () {
            return templateResource.get();
        };
        this.render = function () {
            return renderResource.get({index: dataSetIndex()});
        };
        this.preview = function () {
            return previewResource.get({index: dataSetIndex()});
        };
        this.dataSet = function () {
            return dataSetResource.getList();
        };
        this.dataItem = function () {
            return Restangular.one("rendering/dataSet", dataSetIndex()).get();
        };

        this.pages = pages;
    }


    wbEditor.controller("CodeTabCtrl", CodeTabCtrl);

    CodeTabCtrl.$inject = ['articleService', 'wbAlertService'];

    function CodeTabCtrl(articleService, alertService) {

        var vm = this;

        vm.pages = articleService.pages;

        articleService.dataSet().then(function (data) {
            vm.pages.totalItems = data.length;
        });

        vm.setPage = function (pageNo) {
            vm.pages.currentPage = pageNo;
        };

        vm.pageChanged = function () {
            reloadTab(this.selectedTab);
        };

        vm.tabs = [
            {
                title: 'Data item',
                icon: 'fa-database',
                content: 'dataItem',
                dataPromise: articleService.dataItem
            },
            {
                title: 'Template',
                icon: 'fa-code',
                content: 'editor',
                dataPromise: articleService.template
            },
            {
                title: 'Wiki code',
                icon: 'fa-file-code-o',
                content: 'editor',
                dataPromise: articleService.render
            },
            {
                title: 'Preview',
                icon: 'fa-file-word-o',
                content: 'preview',
                dataPromise: articleService.preview
            }
        ];

        vm.selected = function (tab) {
            vm.tabs.forEach(function (entry) {
                entry.actve = entry === tab;
            });
            this.selectedTab = tab;
            log(tab);
            reloadTab(tab);

        };

        vm.closeAlert = function (alert) {
            alertService.closeAlert(alert);
        };

        function reloadTab(tab) {
            tab.dataPromise().then(function (data) {
                tab.data = data;
            }, function (response) {
                log("Got resp", response.data);
                alertService.add("warning", response.data, 10000);
            });
        }

    }

});

