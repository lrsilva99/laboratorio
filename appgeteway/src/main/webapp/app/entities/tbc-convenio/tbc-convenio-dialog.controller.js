(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_convenioDialogController', Tbc_convenioDialogController);

    Tbc_convenioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_convenio', 'Tbc_instituicao'];

    function Tbc_convenioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_convenio, Tbc_instituicao) {
        var vm = this;

        vm.tbc_convenio = entity;
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
            if (vm.tbc_convenio.id !== null) {
                Tbc_convenio.update(vm.tbc_convenio, onSaveSuccess, onSaveError);
            } else {
                Tbc_convenio.save(vm.tbc_convenio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_convenioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
