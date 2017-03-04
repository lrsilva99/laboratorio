(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_matrizDialogController', Tbc_matrizDialogController);

    Tbc_matrizDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_matriz', 'Tbc_instituicao', 'Tbc_tipo_cadastro'];

    function Tbc_matrizDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_matriz, Tbc_instituicao, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_matriz = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_instituicaos = Tbc_instituicao.query();
        vm.tbc_tipo_cadastros = Tbc_tipo_cadastro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_matriz.id !== null) {
                Tbc_matriz.update(vm.tbc_matriz, onSaveSuccess, onSaveError);
            } else {
                Tbc_matriz.save(vm.tbc_matriz, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_matrizUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
