(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_frases_opcoes', Tbc_frases_opcoes);

    Tbc_frases_opcoes.$inject = ['$resource'];

    function Tbc_frases_opcoes ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-frases-opcoes/:id';

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
