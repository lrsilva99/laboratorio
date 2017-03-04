(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_convenioDeleteController',Tbc_convenioDeleteController);

    Tbc_convenioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_convenio'];

    function Tbc_convenioDeleteController($uibModalInstance, entity, Tbc_convenio) {
        var vm = this;

        vm.tbc_convenio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_convenio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
