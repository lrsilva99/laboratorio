(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_especieDialogController', Tbc_especieDialogController);

    Tbc_especieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_especie', 'Tbc_instituicao', 'Tbc_tipo_cadastro'];

    function Tbc_especieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_especie, Tbc_instituicao, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_especie = entity;
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
            if (vm.tbc_especie.id !== null) {
                Tbc_especie.update(vm.tbc_especie, onSaveSuccess, onSaveError);
            } else {
                Tbc_especie.save(vm.tbc_especie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_especieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
