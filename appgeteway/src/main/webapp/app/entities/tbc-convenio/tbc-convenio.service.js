(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_convenio', Tbc_convenio);

    Tbc_convenio.$inject = ['$resource'];

    function Tbc_convenio ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-convenios/:id';

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
