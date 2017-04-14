(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_generoDialogController', Tbc_generoDialogController);

    Tbc_generoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_genero', 'Tbc_instituicao'];

    function Tbc_generoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_genero, Tbc_instituicao) {
        var vm = this;

        vm.tbc_genero = entity;
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
            if (vm.tbc_genero.id !== null) {
                Tbc_genero.update(vm.tbc_genero, onSaveSuccess, onSaveError);
            } else {
                Tbc_genero.save(vm.tbc_genero, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_generoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
