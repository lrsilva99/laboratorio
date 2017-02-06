(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formularioDeleteController',Tbc_formularioDeleteController);

    Tbc_formularioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_formulario'];

    function Tbc_formularioDeleteController($uibModalInstance, entity, Tbc_formulario) {
        var vm = this;

        vm.tbc_formulario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_formulario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
