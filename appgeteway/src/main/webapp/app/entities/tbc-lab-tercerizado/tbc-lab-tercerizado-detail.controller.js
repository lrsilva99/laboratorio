(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_lab_tercerizadoDetailController', Tbc_lab_tercerizadoDetailController);

    Tbc_lab_tercerizadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_lab_tercerizado', 'Tbc_instituicao'];

    function Tbc_lab_tercerizadoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_lab_tercerizado, Tbc_instituicao) {
        var vm = this;

        vm.tbc_lab_tercerizado = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_lab_tercerizadoUpdate', function(event, result) {
            vm.tbc_lab_tercerizado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
