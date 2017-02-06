(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_lab_tercerizadoSearch', Tbc_lab_tercerizadoSearch);

    Tbc_lab_tercerizadoSearch.$inject = ['$resource'];

    function Tbc_lab_tercerizadoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-lab-tercerizados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
