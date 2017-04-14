(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_formulario_componentes', Tbc_formulario_componentes);

    Tbc_formulario_componentes.$inject = ['$resource'];

    function Tbc_formulario_componentes ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-formulario-componentes/:id';

        return $resource(resourceUrl, {}, {
         'query2': { method: 'GET',
                    isArray: true,
                    transformResponse: function (data) {
                                        if (data) {
                                            data = angular.fromJson(data);
                                        }
                                        return data;
                                        }
                    },
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
