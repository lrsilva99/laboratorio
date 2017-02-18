(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_clienteDialogController', Tbc_clienteDialogController);

    Tbc_clienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_cliente', 'Tbc_instituicao', 'Tbc_grupo_cliente'];

    function Tbc_clienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_cliente, Tbc_instituicao, Tbc_grupo_cliente) {
        var vm = this;

        vm.tbc_cliente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_instituicaos = Tbc_instituicao.query();
        vm.tbc_grupo_clientes = Tbc_grupo_cliente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_cliente.id !== null) {
                Tbc_cliente.update(vm.tbc_cliente, onSaveSuccess, onSaveError);
            } else {
                Tbc_cliente.save(vm.tbc_cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.directiva_data_atu = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
