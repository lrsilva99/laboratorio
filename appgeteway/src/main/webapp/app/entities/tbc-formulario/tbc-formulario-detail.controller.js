(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_formularioDetailController', Tbc_formularioDetailController);

    Tbc_formularioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_formulario', 'Tbc_instituicao', 'Tbc_sub_grupo', 'Tbc_tipo_cadastro'];

    function Tbc_formularioDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_formulario, Tbc_instituicao, Tbc_sub_grupo, Tbc_tipo_cadastro) {
        var vm = this;

        vm.tbc_formulario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_formularioUpdate', function(event, result) {
            vm.tbc_formulario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
