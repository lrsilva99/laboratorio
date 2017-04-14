(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analise_resultadoDetailController', Tbc_analise_resultadoDetailController);

    Tbc_analise_resultadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_analise_resultado', 'Tbc_status'];

    function Tbc_analise_resultadoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_analise_resultado, Tbc_status) {
        var vm = this;

        vm.tbc_analise_resultado = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_analise_resultadoUpdate', function(event, result) {
            vm.tbc_analise_resultado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
