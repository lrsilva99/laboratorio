(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_clienteDetailController', Tbc_clienteDetailController);

    Tbc_clienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_cliente', 'Tbc_instituicao', 'Tbc_grupo_cliente'];

    function Tbc_clienteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_cliente, Tbc_instituicao, Tbc_grupo_cliente) {
        var vm = this;

        vm.tbc_cliente = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_clienteUpdate', function(event, result) {
            vm.tbc_cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
