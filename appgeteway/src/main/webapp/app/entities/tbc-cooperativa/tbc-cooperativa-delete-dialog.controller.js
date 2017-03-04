(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_cooperativaDeleteController',Tbc_cooperativaDeleteController);

    Tbc_cooperativaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_cooperativa'];

    function Tbc_cooperativaDeleteController($uibModalInstance, entity, Tbc_cooperativa) {
        var vm = this;

        vm.tbc_cooperativa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_cooperativa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
