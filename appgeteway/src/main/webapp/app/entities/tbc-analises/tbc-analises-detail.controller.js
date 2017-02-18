(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_analisesDetailController', Tbc_analisesDetailController);

    Tbc_analisesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_analises', 'Tbc_sub_grupo', 'Tbc_grupo_analise', 'Tbc_tipo_cadastro', 'Tbc_instituicao', 'Tbc_report'];

    function Tbc_analisesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_analises, Tbc_sub_grupo, Tbc_grupo_analise, Tbc_tipo_cadastro, Tbc_instituicao, Tbc_report) {
        var vm = this;

        vm.tbc_analises = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_analisesUpdate', function(event, result) {
            vm.tbc_analises = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
