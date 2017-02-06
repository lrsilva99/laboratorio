(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analisesDeleteController',Tbc_analisesDeleteController);

    Tbc_analisesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_analises'];

    function Tbc_analisesDeleteController($uibModalInstance, entity, Tbc_analises) {
        var vm = this;

        vm.tbc_analises = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_analises.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
