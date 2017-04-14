(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_amostraDetailController', Tbr_amostraDetailController);

    Tbr_amostraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tbr_amostra', 'Tbc_especie', 'Tbc_sub_grupo', 'Tbc_status', 'Tbc_cliente', 'Tbc_proprietario', 'Tbc_cooperativa', 'Tbc_fazenda', 'Tbc_forma_armazenamento', 'Tbc_formulario', 'Tbc_convenio', 'Tbc_genero', 'Tbc_qualidade_amostra', 'Tbr_analise'];

    function Tbr_amostraDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tbr_amostra, Tbc_especie, Tbc_sub_grupo, Tbc_status, Tbc_cliente, Tbc_proprietario, Tbc_cooperativa, Tbc_fazenda, Tbc_forma_armazenamento, Tbc_formulario, Tbc_convenio, Tbc_genero, Tbc_qualidade_amostra, Tbr_analise) {
        var vm = this;

        vm.tbr_amostra = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('appgetewayApp:tbr_amostraUpdate', function(event, result) {
            vm.tbr_amostra = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
