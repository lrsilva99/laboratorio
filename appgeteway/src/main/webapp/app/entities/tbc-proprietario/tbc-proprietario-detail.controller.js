(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_proprietarioDetailController', Tbc_proprietarioDetailController);

    Tbc_proprietarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_proprietario', 'Tbc_instituicao'];

    function Tbc_proprietarioDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_proprietario, Tbc_instituicao) {
        var vm = this;

        vm.tbc_proprietario = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_proprietarioUpdate', function(event, result) {
            vm.tbc_proprietario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
