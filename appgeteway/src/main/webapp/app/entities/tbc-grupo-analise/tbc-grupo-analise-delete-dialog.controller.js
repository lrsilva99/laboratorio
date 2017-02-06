(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_analiseDeleteController',Tbc_grupo_analiseDeleteController);

    Tbc_grupo_analiseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_grupo_analise'];

    function Tbc_grupo_analiseDeleteController($uibModalInstance, entity, Tbc_grupo_analise) {
        var vm = this;

        vm.tbc_grupo_analise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_grupo_analise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
