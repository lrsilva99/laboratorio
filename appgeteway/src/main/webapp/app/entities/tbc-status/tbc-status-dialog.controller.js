(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_statusDialogController', Tbc_statusDialogController);

    Tbc_statusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_status'];

    function Tbc_statusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_status) {
        var vm = this;

        vm.tbc_status = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_status.id !== null) {
                Tbc_status.update(vm.tbc_status, onSaveSuccess, onSaveError);
            } else {
                Tbc_status.save(vm.tbc_status, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_statusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
