(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_generoDetailController', Tbc_generoDetailController);

    Tbc_generoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_genero', 'Tbc_instituicao'];

    function Tbc_generoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_genero, Tbc_instituicao) {
        var vm = this;

        vm.tbc_genero = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_generoUpdate', function(event, result) {
            vm.tbc_genero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
