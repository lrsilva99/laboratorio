(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_convenioDetailController', Tbc_convenioDetailController);

    Tbc_convenioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_convenio', 'Tbc_instituicao'];

    function Tbc_convenioDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_convenio, Tbc_instituicao) {
        var vm = this;

        vm.tbc_convenio = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_convenioUpdate', function(event, result) {
            vm.tbc_convenio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
