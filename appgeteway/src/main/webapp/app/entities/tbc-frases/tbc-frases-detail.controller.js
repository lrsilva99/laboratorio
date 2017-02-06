(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbc_frasesDetailController', Tbc_frasesDetailController);

    Tbc_frasesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tbc_frases', 'Tbc_instituicao', 'Tbc_sub_grupo', 'Tbc_frases_opcoes'];

    function Tbc_frasesDetailController($scope, $rootScope, $stateParams, previousState, entity, Tbc_frases, Tbc_instituicao, Tbc_sub_grupo, Tbc_frases_opcoes) {
        var vm = this;

        vm.tbc_frases = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbc_frasesUpdate', function(event, result) {
            vm.tbc_frases = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
