(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_especie', Tbc_especie);

    Tbc_especie.$inject = ['$resource'];

    function Tbc_especie ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-especies/:id';

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
