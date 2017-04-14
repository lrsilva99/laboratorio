(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_genero', Tbc_genero);

    Tbc_genero.$inject = ['$resource'];

    function Tbc_genero ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-generos/:id';

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
