(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_amostraController', Tbr_amostraController);

    Tbr_amostraController.$inject = ['$scope', '$state', 'DataUtils', 'Tbr_amostra', 'Tbr_amostraSearch', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','Tbc_formulario'];

    function Tbr_amostraController ($scope, $state, DataUtils, Tbr_amostra, Tbr_amostraSearch, ParseLinks, AlertService, paginationConstants, pagingParams, Tbc_formulario) {
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
        vm.tbc_formularios;
        vm.visible = false;
        vm.space = "  ";

        vm.carregaFormulario = function (){
        if (vm.tbc_formularios === undefined )
               vm.tbc_formularios = Tbc_formulario.query();
        }
       loadAll();

        function loadAll () {
            if (pagingParams.search) {
                Tbr_amostraSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Tbr_amostra.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
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
                vm.tbr_amostras = data;
                vm.page = pagingParams.page;
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
                search: vm.currentSearch
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
