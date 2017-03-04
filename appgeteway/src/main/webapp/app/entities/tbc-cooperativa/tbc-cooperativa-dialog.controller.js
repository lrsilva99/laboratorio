(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_cooperativaDialogController', Tbc_cooperativaDialogController);

    Tbc_cooperativaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_cooperativa', 'Tbc_instituicao'];

    function Tbc_cooperativaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_cooperativa, Tbc_instituicao) {
        var vm = this;

        vm.tbc_cooperativa = entity;
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
            if (vm.tbc_cooperativa.id !== null) {
                Tbc_cooperativa.update(vm.tbc_cooperativa, onSaveSuccess, onSaveError);
            } else {
                Tbc_cooperativa.save(vm.tbc_cooperativa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_cooperativaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
