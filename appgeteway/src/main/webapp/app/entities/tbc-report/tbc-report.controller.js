(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_reportController', Tbc_reportController);

    Tbc_reportController.$inject = ['$scope', '$state', 'DataUtils', 'Tbc_report', 'Tbc_reportSearch'];

    function Tbc_reportController ($scope, $state, DataUtils, Tbc_report, Tbc_reportSearch) {
        var vm = this;

        vm.tbc_reports = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Tbc_report.query(function(result) {
                vm.tbc_reports = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Tbc_reportSearch.query({query: vm.searchQuery}, function(result) {
                vm.tbc_reports = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
