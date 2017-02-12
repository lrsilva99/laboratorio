(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_plano_teste', Tbc_plano_teste);

    Tbc_plano_teste.$inject = ['$resource'];

    function Tbc_plano_teste ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-plano-testes/:id';

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
