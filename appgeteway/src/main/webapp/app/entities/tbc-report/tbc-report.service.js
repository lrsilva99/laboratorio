(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_report', Tbc_report);

    Tbc_report.$inject = ['$resource'];

    function Tbc_report ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-reports/:id';

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
