(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_especieDetailController', Tbc_especieDetailController);

    Tbc_especieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_especie', 'Tbc_instituicao', 'Tbc_tipo_cadastro'];

    function Tbc_especieDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_especie, Tbc_instituicao, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_especie = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_especieUpdate', function(event, result) {
            vm.tbc_especie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
