(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analises_componenteDialogController', Tbc_analises_componenteDialogController);

    Tbc_analises_componenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_analises_componente', 'Tbc_instituicao', 'Tbc_tipo_campo', 'Tbc_analises'];

    function Tbc_analises_componenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_analises_componente, Tbc_instituicao, Tbc_tipo_campo, Tbc_analises) {
        var vm = this;

        vm.tbc_analises_componente = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_instituicaos = Tbc_instituicao.query();
        vm.tbc_tipo_campos = Tbc_tipo_campo.query();
        vm.tbc_analises = Tbc_analises.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_analises_componente.id !== null) {
                Tbc_analises_componente.update(vm.tbc_analises_componente, onSaveSuccess, onSaveError);
            } else {
                Tbc_analises_componente.save(vm.tbc_analises_componente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_analises_componenteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
