(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_teste_analiseDeleteController',Tbc_plano_teste_analiseDeleteController);

    Tbc_plano_teste_analiseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_plano_teste_analise'];

    function Tbc_plano_teste_analiseDeleteController($uibModalInstance, entity, Tbc_plano_teste_analise) {
        var vm = this;

        vm.tbc_plano_teste_analise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_plano_teste_analise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
