(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_especieDeleteController',Tbc_especieDeleteController);

    Tbc_especieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_especie'];

    function Tbc_especieDeleteController($uibModalInstance, entity, Tbc_especie) {
        var vm = this;

        vm.tbc_especie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_especie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
