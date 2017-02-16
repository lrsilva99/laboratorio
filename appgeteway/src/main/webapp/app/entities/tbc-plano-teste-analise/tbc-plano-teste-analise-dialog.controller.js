(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_teste_analiseDialogController', Tbc_plano_teste_analiseDialogController);

    Tbc_plano_teste_analiseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tbc_plano_teste_analise', 'Tbc_plano_teste', 'Tbc_analises', 'Tbc_analisesSearch'];

    function Tbc_plano_teste_analiseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tbc_plano_teste_analise, Tbc_plano_teste, Tbc_analises) {
        var vm = this;

        vm.tbc_plano_teste_analise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbc_analises = loadOtherEntities ("*");
        vm.idPlanoTeste = $stateParams.idPlanoTeste;
        vm.planoTeste = Tbc_plano_teste.get({id : vm.idPlanoTeste});
        vm.tbc_plano_teste_analise.tbc_plano_teste = vm.planoTeste;



        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tbc_plano_teste_analise.id !== null) {
                Tbc_plano_teste_analise.update(vm.tbc_plano_teste_analise, onSaveSuccess, onSaveError);
            } else {
                Tbc_plano_teste_analise.save(vm.tbc_plano_teste_analise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbc_plano_teste_analiseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function loadOtherEntities (searchQuery) {
        if (searchQuery){
            Tbc_analises.query({query:  searchQuery,
              size: 10000,
            }, function(result) {
                vm.tbc_analises = result;
            }, function(response) {
                if(response.status === 404) {
                    vm.tbc_analises = Tbc_analises.query({nome: searchQuery});
                }
            });
        } else {
             vm.tbc_analises = Tbc_analises.query({nome: searchQuery});
        }
    };
    }



})();
