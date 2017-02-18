(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_plano_teste_analise', Tbc_plano_teste_analise);

    Tbc_plano_teste_analise.$inject = ['$resource'];

    function Tbc_plano_teste_analise ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-plano-teste-analises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
