(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formulario_componentesDetailController', Tbc_formulario_componentesDetailController);

    Tbc_formulario_componentesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_formulario_componentes', 'Tbc_formulario', 'Tbc_tipo_campo'];

    function Tbc_formulario_componentesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_formulario_componentes, Tbc_formulario, Tbc_tipo_campo) {
        var vm = this;

        vm.tbc_formulario_componentes = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_formulario_componentesUpdate', function(event, result) {
            vm.tbc_formulario_componentes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
