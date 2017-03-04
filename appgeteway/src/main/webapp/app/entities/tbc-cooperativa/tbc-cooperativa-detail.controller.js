(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_cooperativaDetailController', Tbc_cooperativaDetailController);

    Tbc_cooperativaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_cooperativa', 'Tbc_instituicao'];

    function Tbc_cooperativaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_cooperativa, Tbc_instituicao) {
        var vm = this;

        vm.tbc_cooperativa = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_cooperativaUpdate', function(event, result) {
            vm.tbc_cooperativa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
