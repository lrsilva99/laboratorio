(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_qualidade_amostra', Tbc_qualidade_amostra);

    Tbc_qualidade_amostra.$inject = ['$resource'];

    function Tbc_qualidade_amostra ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-qualidade-amostras/:id';

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
