(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_frases', Tbc_frases);

    Tbc_frases.$inject = ['$resource'];

    function Tbc_frases ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-frases/:id';

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
            }
        });
    }
})();
