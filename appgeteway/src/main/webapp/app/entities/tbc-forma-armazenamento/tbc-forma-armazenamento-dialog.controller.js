(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_forma_armazenamentoDialogController', Tbc_forma_armazenamentoDialogController);

    Tbc_forma_armazenamentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_forma_armazenamento', 'Tbc_instituicao'];

    function Tbc_forma_armazenamentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_forma_armazenamento, Tbc_instituicao) {
        var vm = this;

        vm.tbc_forma_armazenamento = entity;
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
            if (vm.tbc_forma_armazenamento.id !== null) {
                Tbc_forma_armazenamento.update(vm.tbc_forma_armazenamento, onSaveSuccess, onSaveError);
            } else {
                Tbc_forma_armazenamento.save(vm.tbc_forma_armazenamento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_forma_armazenamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
