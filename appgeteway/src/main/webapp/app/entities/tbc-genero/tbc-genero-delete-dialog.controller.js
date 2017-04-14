(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_generoDeleteController',Tbc_generoDeleteController);

    Tbc_generoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_genero'];

    function Tbc_generoDeleteController($uibModalInstance, entity, Tbc_genero) {
        var vm = this;

        vm.tbc_genero = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_genero.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
