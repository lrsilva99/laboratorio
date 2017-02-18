(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analises_componenteDeleteController',Tbc_analises_componenteDeleteController);

    Tbc_analises_componenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_analises_componente'];

    function Tbc_analises_componenteDeleteController($uibModalInstance, entity, Tbc_analises_componente) {
        var vm = this;

        vm.tbc_analises_componente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_analises_componente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
