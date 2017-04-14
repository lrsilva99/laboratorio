(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_statusDetailController', Tbc_statusDetailController);

    Tbc_statusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_status'];

    function Tbc_statusDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_status) {
        var vm = this;

        vm.tbc_status = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_statusUpdate', function(event, result) {
            vm.tbc_status = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
