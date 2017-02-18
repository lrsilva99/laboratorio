(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_sub_grupoDeleteController',Tbc_sub_grupoDeleteController);

    Tbc_sub_grupoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_sub_grupo'];

    function Tbc_sub_grupoDeleteController($uibModalInstance, entity, Tbc_sub_grupo) {
        var vm = this;

        vm.tbc_sub_grupo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_sub_grupo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
