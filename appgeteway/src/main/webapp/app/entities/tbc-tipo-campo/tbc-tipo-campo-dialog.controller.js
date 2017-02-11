(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_campoDialogController', Tbc_tipo_campoDialogController);

    Tbc_tipo_campoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_tipo_campo'];

    function Tbc_tipo_campoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_tipo_campo) {
        var vm = this;

        vm.tbc_tipo_campo = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_tipo_campo.id !== null) {
                Tbc_tipo_campo.update(vm.tbc_tipo_campo, onSaveSuccess, onSaveError);
            } else {
                Tbc_tipo_campo.save(vm.tbc_tipo_campo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_tipo_campoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
