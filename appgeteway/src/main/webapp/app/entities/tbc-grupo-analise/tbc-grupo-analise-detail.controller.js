(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_grupo_analiseDetailController', Tbc_grupo_analiseDetailController);

    Tbc_grupo_analiseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_grupo_analise', 'Tbc_instituicao'];

    function Tbc_grupo_analiseDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_grupo_analise, Tbc_instituicao) {
        var vm = this;

        vm.tbc_grupo_analise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_grupo_analiseUpdate', function(event, result) {
            vm.tbc_grupo_analise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
