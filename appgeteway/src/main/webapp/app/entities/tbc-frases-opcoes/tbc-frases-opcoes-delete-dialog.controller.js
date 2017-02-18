(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frases_opcoesDeleteController',Tbc_frases_opcoesDeleteController);

    Tbc_frases_opcoesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_frases_opcoes'];

    function Tbc_frases_opcoesDeleteController($uibModalInstance, entity, Tbc_frases_opcoes) {
        var vm = this;

        vm.tbc_frases_opcoes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_frases_opcoes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
