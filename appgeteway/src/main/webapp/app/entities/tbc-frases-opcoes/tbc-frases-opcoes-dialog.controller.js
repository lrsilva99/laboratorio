(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frases_opcoesDialogController', Tbc_frases_opcoesDialogController);

    Tbc_frases_opcoesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_frases_opcoes', 'Tbc_frases'];

    function Tbc_frases_opcoesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_frases_opcoes, Tbc_frases) {
        var vm = this;

        vm.tbc_frases_opcoes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbc_frases = Tbc_frases.query();
        vm.idfrase = $stateParams.idfrases;
        vm.tbc_frases_opcoes.tbc_frases = Tbc_frases.get({id: $stateParams.idfrases})

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_frases_opcoes.id !== null) {
                Tbc_frases_opcoes.update(vm.tbc_frases_opcoes, onSaveSuccess, onSaveError);
            } else {
                Tbc_frases_opcoes.save(vm.tbc_frases_opcoes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_frases_opcoesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
