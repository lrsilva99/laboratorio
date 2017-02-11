(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_tipo_campo', Tbc_tipo_campo);

    Tbc_tipo_campo.$inject = ['$resource'];

    function Tbc_tipo_campo ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-tipo-campos/:id';

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
