(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_sub_grupoDialogController', Tbc_sub_grupoDialogController);

    Tbc_sub_grupoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_sub_grupo', 'Tbc_instituicao'];

    function Tbc_sub_grupoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_sub_grupo, Tbc_instituicao) {
        var vm = this;

        vm.tbc_sub_grupo = entity;
        vm.clear = clear;
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
            if (vm.tbc_sub_grupo.id !== null) {
                Tbc_sub_grupo.update(vm.tbc_sub_grupo, onSaveSuccess, onSaveError);
            } else {
                Tbc_sub_grupo.save(vm.tbc_sub_grupo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_sub_grupoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
