(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_fazenda', Tbc_fazenda);

    Tbc_fazenda.$inject = ['$resource'];

    function Tbc_fazenda ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-fazendas/:id';

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
