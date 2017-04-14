(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_status', Tbc_status);

    Tbc_status.$inject = ['$resource'];

    function Tbc_status ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-statuses/:id';

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
