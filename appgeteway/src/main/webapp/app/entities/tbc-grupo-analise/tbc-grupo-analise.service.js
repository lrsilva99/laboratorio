(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_grupo_analise', Tbc_grupo_analise);

    Tbc_grupo_analise.$inject = ['$resource'];

    function Tbc_grupo_analise ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-grupo-analises/:id';

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
