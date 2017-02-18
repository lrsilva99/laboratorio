(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_grupo_clienteSearch', Tbc_grupo_clienteSearch);

    Tbc_grupo_clienteSearch.$inject = ['$resource'];

    function Tbc_grupo_clienteSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-grupo-clientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
