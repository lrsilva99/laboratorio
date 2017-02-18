(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_sub_grupoDetailController', Tbc_sub_grupoDetailController);

    Tbc_sub_grupoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_sub_grupo', 'Tbc_instituicao'];

    function Tbc_sub_grupoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_sub_grupo, Tbc_instituicao) {
        var vm = this;

        vm.tbc_sub_grupo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_sub_grupoUpdate', function(event, result) {
            vm.tbc_sub_grupo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
