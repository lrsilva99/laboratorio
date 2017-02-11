(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_clienteDeleteController',Tbc_clienteDeleteController);

    Tbc_clienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_cliente'];

    function Tbc_clienteDeleteController($uibModalInstance, entity, Tbc_cliente) {
        var vm = this;

        vm.tbc_cliente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_cliente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
