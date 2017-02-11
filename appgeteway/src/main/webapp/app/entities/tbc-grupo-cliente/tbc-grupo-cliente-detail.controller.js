(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_clienteDetailController', Tbc_grupo_clienteDetailController);

    Tbc_grupo_clienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_grupo_cliente', 'Tbc_instituicao'];

    function Tbc_grupo_clienteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_grupo_cliente, Tbc_instituicao) {
        var vm = this;

        vm.tbc_grupo_cliente = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_grupo_clienteUpdate', function(event, result) {
            vm.tbc_grupo_cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
