(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_cadastroDetailController', Tbc_tipo_cadastroDetailController);

    Tbc_tipo_cadastroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_tipo_cadastro', 'Tbc_instituicao'];

    function Tbc_tipo_cadastroDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_tipo_cadastro, Tbc_instituicao) {
        var vm = this;

        vm.tbc_tipo_cadastro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_tipo_cadastroUpdate', function(event, result) {
            vm.tbc_tipo_cadastro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
