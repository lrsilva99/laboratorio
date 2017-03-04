(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_proprietarioDeleteController',Tbc_proprietarioDeleteController);

    Tbc_proprietarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_proprietario'];

    function Tbc_proprietarioDeleteController($uibModalInstance, entity, Tbc_proprietario) {
        var vm = this;

        vm.tbc_proprietario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_proprietario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
