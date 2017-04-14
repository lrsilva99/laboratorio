(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analise_resultadoController', Tbc_analise_resultadoController);

    Tbc_analise_resultadoController.$inject = ['$scope', '$state', 'DataUtils', 'Tbc_analise_resultado', 'Tbc_analise_resultadoSearch'];

    function Tbc_analise_resultadoController ($scope, $state, DataUtils, Tbc_analise_resultado, Tbc_analise_resultadoSearch) {
        var vm = this;

        vm.tbc_analise_resultados = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Tbc_analise_resultado.query(function(result) {
                vm.tbc_analise_resultados = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Tbc_analise_resultadoSearch.query({query: vm.searchQuery}, function(result) {
                vm.tbc_analise_resultados = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
