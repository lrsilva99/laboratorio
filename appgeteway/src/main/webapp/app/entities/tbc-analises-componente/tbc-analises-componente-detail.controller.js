(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analises_componenteDetailController', Tbc_analises_componenteDetailController);

    Tbc_analises_componenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_analises_componente', 'Tbc_instituicao', 'Tbc_tipo_campo', 'Tbc_analises'];

    function Tbc_analises_componenteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_analises_componente, Tbc_instituicao, Tbc_tipo_campo, Tbc_analises) {
        var vm = this;

        vm.tbc_analises_componente = entity;
        vm.previousState = previousState.name.concat("({idAnalise:",vm.tbc_analises_componente.tbc_analises.id,"})");
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_analises_componenteUpdate', function(event, result) {
            vm.tbc_analises_componente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
