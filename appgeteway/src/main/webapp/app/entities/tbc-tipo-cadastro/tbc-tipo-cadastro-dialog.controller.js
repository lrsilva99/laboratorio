(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_cadastroDialogController', Tbc_tipo_cadastroDialogController);

    Tbc_tipo_cadastroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_tipo_cadastro', 'Tbc_instituicao'];

    function Tbc_tipo_cadastroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_tipo_cadastro, Tbc_instituicao) {
        var vm = this;

        vm.tbc_tipo_cadastro = entity;
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
            if (vm.tbc_tipo_cadastro.id !== null) {
                Tbc_tipo_cadastro.update(vm.tbc_tipo_cadastro, onSaveSuccess, onSaveError);
            } else {
                Tbc_tipo_cadastro.save(vm.tbc_tipo_cadastro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_tipo_cadastroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
