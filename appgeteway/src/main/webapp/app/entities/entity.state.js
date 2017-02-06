(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('entity', {
            abstract: true,
            parent: 'app'
        }),
        $stateProvider.state('laboratorio', {
                    abstract: true,
                    parent: 'app'
                });
        ;
    }
})();
