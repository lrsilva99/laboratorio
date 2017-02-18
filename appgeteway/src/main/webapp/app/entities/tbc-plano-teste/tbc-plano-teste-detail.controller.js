(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_testeDetailController', Tbc_plano_testeDetailController);

    Tbc_plano_testeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_plano_teste', 'Tbc_tipo_cadastro', 'Tbc_sub_grupo', 'Tbc_instituicao'];

    function Tbc_plano_testeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_plano_teste, Tbc_tipo_cadastro, Tbc_sub_grupo, Tbc_instituicao) {
        var vm = this;

        vm.tbc_plano_teste = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_plano_testeUpdate', function(event, result) {
            vm.tbc_plano_teste = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
