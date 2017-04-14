(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formulario_componentesDialogController', Tbc_formulario_componentesDialogController);

    Tbc_formulario_componentesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_formulario_componentes', 'Tbc_formulario', 'Tbc_tipo_campo'];

    function Tbc_formulario_componentesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_formulario_componentes, Tbc_formulario, Tbc_tipo_campo) {
        var vm = this;

        vm.tbc_formulario_componentes = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_formularios = Tbc_formulario.query();
        vm.tbc_tipo_campos = Tbc_tipo_campo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_formulario_componentes.id !== null) {
                Tbc_formulario_componentes.update(vm.tbc_formulario_componentes, onSaveSuccess, onSaveError);
            } else {
                Tbc_formulario_componentes.save(vm.tbc_formulario_componentes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_formulario_componentesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
