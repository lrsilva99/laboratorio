(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_lab_tercerizadoDeleteController',Tbc_lab_tercerizadoDeleteController);

    Tbc_lab_tercerizadoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_lab_tercerizado'];

    function Tbc_lab_tercerizadoDeleteController($uibModalInstance, entity, Tbc_lab_tercerizado) {
        var vm = this;

        vm.tbc_lab_tercerizado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_lab_tercerizado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
