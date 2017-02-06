(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analisesDialogController', Tbc_analisesDialogController);

    Tbc_analisesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_analises', 'Tbc_sub_grupo', 'Tbc_grupo_analise', 'Tbc_tipo_cadastro', 'Tbc_instituicao', 'Tbc_report'];

    function Tbc_analisesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_analises, Tbc_sub_grupo, Tbc_grupo_analise, Tbc_tipo_cadastro, Tbc_instituicao, Tbc_report) {
        var vm = this;

        vm.tbc_analises = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_sub_grupos = Tbc_sub_grupo.query();
        vm.tbc_grupo_analises = Tbc_grupo_analise.query();
        vm.tbc_tipo_cadastros = Tbc_tipo_cadastro.query();
        vm.tbc_instituicaos = Tbc_instituicao.query();
        vm.tbc_reports = Tbc_report.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_analises.id !== null) {
                Tbc_analises.update(vm.tbc_analises, onSaveSuccess, onSaveError);
            } else {
                Tbc_analises.save(vm.tbc_analises, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_analisesUpdate', result);
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
