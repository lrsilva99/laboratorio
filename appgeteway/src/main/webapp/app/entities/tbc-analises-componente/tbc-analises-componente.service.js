(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_analises_componente', Tbc_analises_componente);

    Tbc_analises_componente.$inject = ['$resource'];

    function Tbc_analises_componente ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-analises-componentes/:id';

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
