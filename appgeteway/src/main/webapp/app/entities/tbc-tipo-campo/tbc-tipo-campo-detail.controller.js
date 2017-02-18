(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_tipo_campoDetailController', Tbc_tipo_campoDetailController);

    Tbc_tipo_campoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_tipo_campo'];

    function Tbc_tipo_campoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_tipo_campo) {
        var vm = this;

        vm.tbc_tipo_campo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_tipo_campoUpdate', function(event, result) {
            vm.tbc_tipo_campo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
