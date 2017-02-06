(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_instituicaoDetailController', Tbc_instituicaoDetailController);

    Tbc_instituicaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_instituicao'];

    function Tbc_instituicaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_instituicao) {
        var vm = this;

        vm.tbc_instituicao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_instituicaoUpdate', function(event, result) {
            vm.tbc_instituicao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
