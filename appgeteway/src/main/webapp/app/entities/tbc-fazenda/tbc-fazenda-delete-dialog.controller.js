(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_fazendaDeleteController',Tbc_fazendaDeleteController);

    Tbc_fazendaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_fazenda'];

    function Tbc_fazendaDeleteController($uibModalInstance, entity, Tbc_fazenda) {
        var vm = this;

        vm.tbc_fazenda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_fazenda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
