(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_forma_armazenamentoDetailController', Tbc_forma_armazenamentoDetailController);

    Tbc_forma_armazenamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_forma_armazenamento', 'Tbc_instituicao'];

    function Tbc_forma_armazenamentoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_forma_armazenamento, Tbc_instituicao) {
        var vm = this;

        vm.tbc_forma_armazenamento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_forma_armazenamentoUpdate', function(event, result) {
            vm.tbc_forma_armazenamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
