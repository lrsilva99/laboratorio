(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_clienteDialogController', Tbc_grupo_clienteDialogController);

    Tbc_grupo_clienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_grupo_cliente', 'Tbc_instituicao'];

    function Tbc_grupo_clienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_grupo_cliente, Tbc_instituicao) {
        var vm = this;

        vm.tbc_grupo_cliente = entity;
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
            if (vm.tbc_grupo_cliente.id !== null) {
                Tbc_grupo_cliente.update(vm.tbc_grupo_cliente, onSaveSuccess, onSaveError);
            } else {
                Tbc_grupo_cliente.save(vm.tbc_grupo_cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_grupo_clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
