(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analises_componenteController', Tbc_analises_componenteController);

    Tbc_analises_componenteController.$inject = ['$scope', '$state', 'DataUtils', 'Tbc_analises_componente', 'Tbc_analises_componenteSearch', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Tbc_analises'];

    function Tbc_analises_componenteController ($scope, $state, DataUtils, Tbc_analises_componente, Tbc_analises_componenteSearch, ParseLinks, AlertService, paginationConstants, pagingParams, Tbc_analises) {
        var vm = this;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.idAnalise = pagingParams.idAnalise;
        vm.Analise =  Tbc_analises.get({id : pagingParams.idAnalise});
        loadAll();

        function loadAll () {
            if (pagingParams.search) {
                Tbc_analises_componenteSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    idAnalise:  pagingParams.idAnalise
                }, onSuccess, onError);
            } else {
                Tbc_analises_componente.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    idAnalise: pagingParams.idAnalise
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
                vm.tbc_analises_componentes = data;
                vm.page = pagingParams.page;
                vm.idAnalise = pagingParams.idAnalise;
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
                idAnalise: vm.idAnalise
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
