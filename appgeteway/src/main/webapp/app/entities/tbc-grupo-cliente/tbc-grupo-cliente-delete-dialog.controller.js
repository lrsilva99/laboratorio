(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_clienteDeleteController',Tbc_grupo_clienteDeleteController);

    Tbc_grupo_clienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_grupo_cliente'];

    function Tbc_grupo_clienteDeleteController($uibModalInstance, entity, Tbc_grupo_cliente) {
        var vm = this;

        vm.tbc_grupo_cliente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_grupo_cliente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
