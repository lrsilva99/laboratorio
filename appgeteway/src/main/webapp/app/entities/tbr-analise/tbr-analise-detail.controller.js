(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analiseDetailController', Tbr_analiseDetailController);

    Tbr_analiseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbr_analise', 'Tbr_amostra', 'Tbc_status', 'Tbc_analises'];

    function Tbr_analiseDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbr_analise, Tbr_amostra, Tbc_status, Tbc_analises) {
        var vm = this;

        vm.tbr_analise = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbr_analiseUpdate', function(event, result) {
            vm.tbr_analise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
