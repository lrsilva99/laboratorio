(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analise_resultadoDialogController', Tbr_analise_resultadoDialogController);

    Tbr_analise_resultadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbr_analise_resultado', 'Tbc_status', 'Tbc_analises_componente'];

    function Tbr_analise_resultadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbr_analise_resultado, Tbc_status, Tbc_analises_componente) {
        var vm = this;

        vm.tbr_analise_resultado = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_statuses = Tbc_status.query();
        vm.tbc_analises_componentes = Tbc_analises_componente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbr_analise_resultado.id !== null) {
                Tbr_analise_resultado.update(vm.tbr_analise_resultado, onSaveSuccess, onSaveError);
            } else {
                Tbr_analise_resultado.save(vm.tbr_analise_resultado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbr_analise_resultadoUpdate', result);
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
