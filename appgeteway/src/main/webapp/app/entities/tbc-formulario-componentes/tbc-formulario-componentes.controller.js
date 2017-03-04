(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formulario_componentesController', Tbc_formulario_componentesController);

    Tbc_formulario_componentesController.$inject = ['$scope', '$state', 'DataUtils', 'Tbc_formulario_componentes', 'Tbc_formulario_componentesSearch', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Tbc_formulario'];

    function Tbc_formulario_componentesController ($scope, $state, DataUtils, Tbc_formulario_componentes, Tbc_formulario_componentesSearch, ParseLinks, AlertService, paginationConstants, pagingParams, Tbc_formulario) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
         vm.Formulario =  Tbc_formulario.get({id : pagingParams.idFormulario});
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.idFormulario = pagingParams.idFormulario;
        loadAll();

        function loadAll () {
            if (pagingParams.search) {
                Tbc_formulario_componentesSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    idFormulario:  vm.idFormulario
                }, onSuccess, onError);
            } else {
                Tbc_formulario_componentes.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    idFormulario:  vm.idFormulario
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.tbc_formulario_componentes = data;
                vm.page = pagingParams.page;
                vm.idFormulario = pagingParams.idFormulario;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch,
                idFormulario: vm.idFormulario
            });
        }

        function search(searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }
    }
})();
