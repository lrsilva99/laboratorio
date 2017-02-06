(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_reportDialogController', Tbc_reportDialogController);

    Tbc_reportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_report', 'Tbc_instituicao'];

    function Tbc_reportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_report, Tbc_instituicao) {
        var vm = this;

        vm.tbc_report = entity;
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
            if (vm.tbc_report.id !== null) {
                Tbc_report.update(vm.tbc_report, onSaveSuccess, onSaveError);
            } else {
                Tbc_report.save(vm.tbc_report, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_reportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
