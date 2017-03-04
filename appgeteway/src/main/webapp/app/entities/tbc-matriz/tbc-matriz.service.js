(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_matriz', Tbc_matriz);

    Tbc_matriz.$inject = ['$resource'];

    function Tbc_matriz ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-matrizs/:id';

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
