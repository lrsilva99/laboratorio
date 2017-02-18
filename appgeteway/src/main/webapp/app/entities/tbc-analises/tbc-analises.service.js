(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_analises', Tbc_analises);

    Tbc_analises.$inject = ['$resource', 'DateUtils'];

    function Tbc_analises ($resource, DateUtils) {
        var resourceUrl =  'lims/' + 'api/tbc-analises/:id';

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
