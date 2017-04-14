(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_qualidade_amostraDeleteController',Tbc_qualidade_amostraDeleteController);

    Tbc_qualidade_amostraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_qualidade_amostra'];

    function Tbc_qualidade_amostraDeleteController($uibModalInstance, entity, Tbc_qualidade_amostra) {
        var vm = this;

        vm.tbc_qualidade_amostra = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_qualidade_amostra.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
