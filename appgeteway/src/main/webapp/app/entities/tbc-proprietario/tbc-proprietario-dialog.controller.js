(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_proprietarioDialogController', Tbc_proprietarioDialogController);

    Tbc_proprietarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_proprietario', 'Tbc_instituicao'];

    function Tbc_proprietarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_proprietario, Tbc_instituicao) {
        var vm = this;

        vm.tbc_proprietario = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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
            if (vm.tbc_proprietario.id !== null) {
                Tbc_proprietario.update(vm.tbc_proprietario, onSaveSuccess, onSaveError);
            } else {
                Tbc_proprietario.save(vm.tbc_proprietario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_proprietarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
