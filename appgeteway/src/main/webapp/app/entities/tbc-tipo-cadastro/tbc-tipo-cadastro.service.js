(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_tipo_cadastro', Tbc_tipo_cadastro);

    Tbc_tipo_cadastro.$inject = ['$resource'];

    function Tbc_tipo_cadastro ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-tipo-cadastros/:id';

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
