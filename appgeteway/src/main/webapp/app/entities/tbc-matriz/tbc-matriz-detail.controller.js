(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_matrizDetailController', Tbc_matrizDetailController);

    Tbc_matrizDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_matriz', 'Tbc_instituicao', 'Tbc_tipo_cadastro'];

    function Tbc_matrizDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_matriz, Tbc_instituicao, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_matriz = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_matrizUpdate', function(event, result) {
            vm.tbc_matriz = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
