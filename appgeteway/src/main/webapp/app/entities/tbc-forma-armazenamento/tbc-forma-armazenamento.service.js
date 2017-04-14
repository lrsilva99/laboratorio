(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_forma_armazenamento', Tbc_forma_armazenamento);

    Tbc_forma_armazenamento.$inject = ['$resource'];

    function Tbc_forma_armazenamento ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-forma-armazenamentos/:id';

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
