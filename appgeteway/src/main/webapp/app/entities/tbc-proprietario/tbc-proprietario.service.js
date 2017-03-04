(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_proprietario', Tbc_proprietario);

    Tbc_proprietario.$inject = ['$resource'];

    function Tbc_proprietario ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-proprietarios/:id';

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
