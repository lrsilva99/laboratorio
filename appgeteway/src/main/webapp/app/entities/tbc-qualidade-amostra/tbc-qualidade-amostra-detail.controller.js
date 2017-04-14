(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_qualidade_amostraDetailController', Tbc_qualidade_amostraDetailController);

    Tbc_qualidade_amostraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbc_qualidade_amostra', 'Tbc_instituicao'];

    function Tbc_qualidade_amostraDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbc_qualidade_amostra, Tbc_instituicao) {
        var vm = this;

        vm.tbc_qualidade_amostra = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_qualidade_amostraUpdate', function(event, result) {
            vm.tbc_qualidade_amostra = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
