(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_formulario', Tbc_formulario);

    Tbc_formulario.$inject = ['$resource'];

    function Tbc_formulario ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-formularios/:id';

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
