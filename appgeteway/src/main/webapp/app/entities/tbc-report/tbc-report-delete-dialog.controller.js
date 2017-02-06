(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_reportDeleteController',Tbc_reportDeleteController);

    Tbc_reportDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_report'];

    function Tbc_reportDeleteController($uibModalInstance, entity, Tbc_report) {
        var vm = this;

        vm.tbc_report = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_report.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
