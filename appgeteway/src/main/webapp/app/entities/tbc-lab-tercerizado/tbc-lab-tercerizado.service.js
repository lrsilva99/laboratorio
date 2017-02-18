(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbc_lab_tercerizado', Tbc_lab_tercerizado);

    Tbc_lab_tercerizado.$inject = ['$resource'];

    function Tbc_lab_tercerizado ($resource) {
        var resourceUrl =  'lims/' + 'api/tbc-lab-tercerizados/:id';

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
