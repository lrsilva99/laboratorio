(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_sub_grupo', Tbc_sub_grupo);

    Tbc_sub_grupo.$inject = ['$resource'];

    function Tbc_sub_grupo ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-sub-grupos/:id';

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
