(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_amostraDeleteController',Tbr_amostraDeleteController);

    Tbr_amostraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tbr_amostra'];

    function Tbr_amostraDeleteController($uibModalInstance, entity, Tbr_amostra) {
        var vm = this;

        vm.tbr_amostra = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tbr_amostra.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
