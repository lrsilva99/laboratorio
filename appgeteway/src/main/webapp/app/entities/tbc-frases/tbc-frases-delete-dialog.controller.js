(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frasesDeleteController',Tbc_frasesDeleteController);

    Tbc_frasesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbc_frases'];

    function Tbc_frasesDeleteController($uibModalInstance, entity, Tbc_frases) {
        var vm = this;

        vm.tbc_frases = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbc_frases.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
