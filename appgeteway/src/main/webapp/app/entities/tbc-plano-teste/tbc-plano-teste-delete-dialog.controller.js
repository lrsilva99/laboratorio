(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_testeDeleteController',Tbc_plano_testeDeleteController);

    Tbc_plano_testeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_plano_teste'];

    function Tbc_plano_testeDeleteController($uibModalInstance, entity, Tbc_plano_teste) {
        var vm = this;

        vm.tbc_plano_teste = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_plano_teste.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
