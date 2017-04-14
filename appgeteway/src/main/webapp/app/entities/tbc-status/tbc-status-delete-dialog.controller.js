(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_statusDeleteController',Tbc_statusDeleteController);

    Tbc_statusDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_status'];

    function Tbc_statusDeleteController($uibModalInstance, entity, Tbc_status) {
        var vm = this;

        vm.tbc_status = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_status.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
