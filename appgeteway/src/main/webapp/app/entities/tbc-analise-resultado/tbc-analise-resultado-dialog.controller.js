(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analise_resultadoDialogController', Tbc_analise_resultadoDialogController);

    Tbc_analise_resultadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_analise_resultado', 'Tbc_status'];

    function Tbc_analise_resultadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_analise_resultado, Tbc_status) {
        var vm = this;

        vm.tbc_analise_resultado = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_statuses = Tbc_status.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_analise_resultado.id !== null) {
                Tbc_analise_resultado.update(vm.tbc_analise_resultado, onSaveSuccess, onSaveError);
            } else {
                Tbc_analise_resultado.save(vm.tbc_analise_resultado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_analise_resultadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.autorizado_data = false;
        vm.datePickerOpenStatus.cancelado_data = false;
        vm.datePickerOpenStatus.suspenso_data = false;
        vm.datePickerOpenStatus.rejeitado_data = false;
        vm.datePickerOpenStatus.disponivel_data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
