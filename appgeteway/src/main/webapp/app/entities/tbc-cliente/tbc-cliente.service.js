(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_cliente', Tbc_cliente);

    Tbc_cliente.$inject = ['$resource', 'DateUtils'];

    function Tbc_cliente ($resource, DateUtils) {
        var resourceUrl =  'lims/' + 'api/tbc-clientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.directiva_data_atu = DateUtils.convertDateTimeFromServer(data.directiva_data_atu);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
