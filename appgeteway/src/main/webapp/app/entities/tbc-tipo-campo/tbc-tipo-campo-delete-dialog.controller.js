(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_campoDeleteController',Tbc_tipo_campoDeleteController);

    Tbc_tipo_campoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_tipo_campo'];

    function Tbc_tipo_campoDeleteController($uibModalInstance, entity, Tbc_tipo_campo) {
        var vm = this;

        vm.tbc_tipo_campo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_tipo_campo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
