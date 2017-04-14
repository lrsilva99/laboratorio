(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_qualidade_amostraDialogController', Tbc_qualidade_amostraDialogController);

    Tbc_qualidade_amostraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_qualidade_amostra', 'Tbc_instituicao'];

    function Tbc_qualidade_amostraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_qualidade_amostra, Tbc_instituicao) {
        var vm = this;

        vm.tbc_qualidade_amostra = entity;
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
            if (vm.tbc_qualidade_amostra.id !== null) {
                Tbc_qualidade_amostra.update(vm.tbc_qualidade_amostra, onSaveSuccess, onSaveError);
            } else {
                Tbc_qualidade_amostra.save(vm.tbc_qualidade_amostra, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_qualidade_amostraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
