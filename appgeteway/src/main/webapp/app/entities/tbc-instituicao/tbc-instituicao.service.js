(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_instituicao', Tbc_instituicao);

    Tbc_instituicao.$inject = ['$resource'];

    function Tbc_instituicao ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-instituicaos/:id';

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
