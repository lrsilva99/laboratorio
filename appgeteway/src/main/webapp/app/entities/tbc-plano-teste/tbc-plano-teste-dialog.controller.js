(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_testeDialogController', Tbc_plano_testeDialogController);

    Tbc_plano_testeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_plano_teste', 'Tbc_tipo_cadastro', 'Tbc_sub_grupo', 'Tbc_instituicao'];

    function Tbc_plano_testeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_plano_teste, Tbc_tipo_cadastro, Tbc_sub_grupo, Tbc_instituicao) {
        var vm = this;

        vm.tbc_plano_teste = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_tipo_cadastros = Tbc_tipo_cadastro.query();
        vm.tbc_sub_grupos = Tbc_sub_grupo.query();
        vm.tbc_instituicaos = Tbc_instituicao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_plano_teste.id !== null) {
                Tbc_plano_teste.update(vm.tbc_plano_teste, onSaveSuccess, onSaveError);
            } else {
                Tbc_plano_teste.save(vm.tbc_plano_teste, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_plano_testeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
