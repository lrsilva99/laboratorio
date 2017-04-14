(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analiseDeleteController',Tbr_analiseDeleteController);

    Tbr_analiseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbr_analise'];

    function Tbr_analiseDeleteController($uibModalInstance, entity, Tbr_analise) {
        var vm = this;

        vm.tbr_analise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbr_analise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
