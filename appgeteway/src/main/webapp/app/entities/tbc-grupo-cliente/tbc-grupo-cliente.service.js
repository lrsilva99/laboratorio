(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_grupo_cliente', Tbc_grupo_cliente);

    Tbc_grupo_cliente.$inject = ['$resource'];

    function Tbc_grupo_cliente ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-grupo-clientes/:id';

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
