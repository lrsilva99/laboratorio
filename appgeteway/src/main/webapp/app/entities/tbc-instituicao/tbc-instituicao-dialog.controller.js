(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_instituicaoDialogController', Tbc_instituicaoDialogController);

    Tbc_instituicaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_instituicao'];

    function Tbc_instituicaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_instituicao) {
        var vm = this;

        vm.tbc_instituicao = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_instituicao.id !== null) {
                Tbc_instituicao.update(vm.tbc_instituicao, onSaveSuccess, onSaveError);
            } else {
                Tbc_instituicao.save(vm.tbc_instituicao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_instituicaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
