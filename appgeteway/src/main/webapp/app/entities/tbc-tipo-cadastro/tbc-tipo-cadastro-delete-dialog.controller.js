(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_cadastroDeleteController',Tbc_tipo_cadastroDeleteController);

    Tbc_tipo_cadastroDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_tipo_cadastro'];

    function Tbc_tipo_cadastroDeleteController($uibModalInstance, entity, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_tipo_cadastro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_tipo_cadastro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
