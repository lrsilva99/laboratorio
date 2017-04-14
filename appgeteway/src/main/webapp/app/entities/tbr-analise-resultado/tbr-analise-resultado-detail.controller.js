(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analise_resultadoDetailController', Tbr_analise_resultadoDetailController);

    Tbr_analise_resultadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbr_analise_resultado', 'Tbc_status', 'Tbc_analises_componente'];

    function Tbr_analise_resultadoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbr_analise_resultado, Tbc_status, Tbc_analises_componente) {
        var vm = this;

        vm.tbr_analise_resultado = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbr_analise_resultadoUpdate', function(event, result) {
            vm.tbr_analise_resultado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
