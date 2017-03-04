(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_matrizDeleteController',Tbc_matrizDeleteController);

    Tbc_matrizDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_matriz'];

    function Tbc_matrizDeleteController($uibModalInstance, entity, Tbc_matriz) {
        var vm = this;

        vm.tbc_matriz = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_matriz.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
