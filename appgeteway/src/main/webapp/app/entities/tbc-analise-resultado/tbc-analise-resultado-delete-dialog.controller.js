(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analise_resultadoDeleteController',Tbc_analise_resultadoDeleteController);

    Tbc_analise_resultadoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_analise_resultado'];

    function Tbc_analise_resultadoDeleteController($uibModalInstance, entity, Tbc_analise_resultado) {
        var vm = this;

        vm.tbc_analise_resultado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_analise_resultado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
