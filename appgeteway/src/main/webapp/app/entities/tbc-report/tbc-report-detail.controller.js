(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_reportDetailController', Tbc_reportDetailController);

    Tbc_reportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_report', 'Tbc_instituicao'];

    function Tbc_reportDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_report, Tbc_instituicao) {
        var vm = this;

        vm.tbc_report = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_reportUpdate', function(event, result) {
            vm.tbc_report = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
