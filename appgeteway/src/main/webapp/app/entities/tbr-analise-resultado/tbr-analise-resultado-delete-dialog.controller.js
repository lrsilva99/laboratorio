(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analise_resultadoDeleteController',Tbr_analise_resultadoDeleteController);

    Tbr_analise_resultadoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbr_analise_resultado'];

    function Tbr_analise_resultadoDeleteController($uibModalInstance, entity, Tbr_analise_resultado) {
        var vm = this;

        vm.tbr_analise_resultado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbr_analise_resultado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
