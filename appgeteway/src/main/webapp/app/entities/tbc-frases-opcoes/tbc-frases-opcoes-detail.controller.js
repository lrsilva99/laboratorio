(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frases_opcoesDetailController', Tbc_frases_opcoesDetailController);

    Tbc_frases_opcoesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_frases_opcoes', 'Tbc_frases'];

    function Tbc_frases_opcoesDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_frases_opcoes, Tbc_frases) {
        var vm = this;

        vm.tbc_frases_opcoes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_frases_opcoesUpdate', function(event, result) {
            vm.tbc_frases_opcoes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
