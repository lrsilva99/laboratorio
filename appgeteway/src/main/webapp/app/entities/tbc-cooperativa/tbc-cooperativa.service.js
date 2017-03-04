(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_cooperativa', Tbc_cooperativa);

    Tbc_cooperativa.$inject = ['$resource'];

    function Tbc_cooperativa ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-cooperativas/:id';

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
