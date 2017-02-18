(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_teste_analiseDialogController', Tbc_plano_teste_analiseDialogController);

    Tbc_plano_teste_analiseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_plano_teste_analise', 'Tbc_plano_teste', 'Tbc_analises'];

    function Tbc_plano_teste_analiseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_plano_teste_analise, Tbc_plano_teste, Tbc_analises) {
        var vm = this;

        vm.tbc_plano_teste_analise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbc_plano_testes = Tbc_plano_teste.query();
        vm.tbc_analises = Tbc_analises.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_plano_teste_analise.id !== null) {
                Tbc_plano_teste_analise.update(vm.tbc_plano_teste_analise, onSaveSuccess, onSaveError);
            } else {
                Tbc_plano_teste_analise.save(vm.tbc_plano_teste_analise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_plano_teste_analiseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
