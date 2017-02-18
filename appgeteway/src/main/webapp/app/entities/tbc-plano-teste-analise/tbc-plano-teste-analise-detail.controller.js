(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_plano_teste_analiseDetailController', Tbc_plano_teste_analiseDetailController);

    Tbc_plano_teste_analiseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_plano_teste_analise', 'Tbc_plano_teste', 'Tbc_analises'];

    function Tbc_plano_teste_analiseDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_plano_teste_analise, Tbc_plano_teste, Tbc_analises) {
        var vm = this;

        vm.tbc_plano_teste_analise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_plano_teste_analiseUpdate', function(event, result) {
            vm.tbc_plano_teste_analise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
