(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_clienteSearch', Tbc_clienteSearch);

    Tbc_clienteSearch.$inject = ['$resource'];

    function Tbc_clienteSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-clientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
