(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_forma_armazenamentoDeleteController',Tbc_forma_armazenamentoDeleteController);

    Tbc_forma_armazenamentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_forma_armazenamento'];

    function Tbc_forma_armazenamentoDeleteController($uibModalInstance, entity, Tbc_forma_armazenamento) {
        var vm = this;

        vm.tbc_forma_armazenamento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_forma_armazenamento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
