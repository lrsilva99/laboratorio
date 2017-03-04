(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_fazendaDetailController', Tbc_fazendaDetailController);

    Tbc_fazendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_fazenda', 'Tbc_instituicao'];

    function Tbc_fazendaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_fazenda, Tbc_instituicao) {
        var vm = this;

        vm.tbc_fazenda = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_fazendaUpdate', function(event, result) {
            vm.tbc_fazenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
