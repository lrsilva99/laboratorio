(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_fazendaDialogController', Tbc_fazendaDialogController);

    Tbc_fazendaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbc_fazenda', 'Tbc_instituicao'];

    function Tbc_fazendaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbc_fazenda, Tbc_instituicao) {
        var vm = this;

        vm.tbc_fazenda = entity;
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
            if (vm.tbc_fazenda.id !== null) {
                Tbc_fazenda.update(vm.tbc_fazenda, onSaveSuccess, onSaveError);
            } else {
                Tbc_fazenda.save(vm.tbc_fazenda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_fazendaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
