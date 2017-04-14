(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analiseDialogController', Tbr_analiseDialogController);

    Tbr_analiseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbr_analise', 'Tbr_amostra', 'Tbc_status', 'Tbc_analises'];

    function Tbr_analiseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbr_analise, Tbr_amostra, Tbc_status, Tbc_analises) {
        var vm = this;

        vm.tbr_analise = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbr_amostras = Tbr_amostra.query();
        vm.tbc_statuses = Tbc_status.query();
        vm.tbc_analises = Tbc_analises.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbr_analise.id !== null) {
                Tbr_analise.update(vm.tbr_analise, onSaveSuccess, onSaveError);
            } else {
                Tbr_analise.save(vm.tbr_analise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbr_analiseUpdate', result);
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
        vm.datePickerOpenStatus.directiva_data_atu = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
