(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_analiseDialogController', Tbc_grupo_analiseDialogController);

    Tbc_grupo_analiseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_grupo_analise', 'Tbc_instituicao'];

    function Tbc_grupo_analiseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_grupo_analise, Tbc_instituicao) {
        var vm = this;

        vm.tbc_grupo_analise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbc_instituicaos = Tbc_instituicao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_grupo_analise.id !== null) {
                Tbc_grupo_analise.update(vm.tbc_grupo_analise, onSaveSuccess, onSaveError);
            } else {
                Tbc_grupo_analise.save(vm.tbc_grupo_analise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_grupo_analiseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
