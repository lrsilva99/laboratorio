(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_lab_tercerizadoDialogController', Tbc_lab_tercerizadoDialogController);

    Tbc_lab_tercerizadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_lab_tercerizado', 'Tbc_instituicao'];

    function Tbc_lab_tercerizadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_lab_tercerizado, Tbc_instituicao) {
        var vm = this;

        vm.tbc_lab_tercerizado = entity;
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
            if (vm.tbc_lab_tercerizado.id !== null) {
                Tbc_lab_tercerizado.update(vm.tbc_lab_tercerizado, onSaveSuccess, onSaveError);
            } else {
                Tbc_lab_tercerizado.save(vm.tbc_lab_tercerizado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_lab_tercerizadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
