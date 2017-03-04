(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formulario_componentesDeleteController',Tbc_formulario_componentesDeleteController);

    Tbc_formulario_componentesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_formulario_componentes'];

    function Tbc_formulario_componentesDeleteController($uibModalInstance, entity, Tbc_formulario_componentes) {
        var vm = this;

        vm.tbc_formulario_componentes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_formulario_componentes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
