(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frasesDialogController', Tbc_frasesDialogController);

    Tbc_frasesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_frases', 'Tbc_instituicao', 'Tbc_sub_grupo'];

    function Tbc_frasesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_frases, Tbc_instituicao, Tbc_sub_grupo) {
        var vm = this;

        vm.tbc_frases = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbc_instituicaos = Tbc_instituicao.query();
        vm.tbc_sub_grupos = Tbc_sub_grupo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_frases.id !== null) {
                Tbc_frases.update(vm.tbc_frases, onSaveSuccess, onSaveError);
            } else {
                Tbc_frases.save(vm.tbc_frases, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_frasesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
