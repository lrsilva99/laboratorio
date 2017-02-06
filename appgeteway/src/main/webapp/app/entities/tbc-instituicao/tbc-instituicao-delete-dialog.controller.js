(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_instituicaoDeleteController',Tbc_instituicaoDeleteController);

    Tbc_instituicaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_instituicao'];

    function Tbc_instituicaoDeleteController($uibModalInstance, entity, Tbc_instituicao) {
        var vm = this;

        vm.tbc_instituicao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_instituicao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
