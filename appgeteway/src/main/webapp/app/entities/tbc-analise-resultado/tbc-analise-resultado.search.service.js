(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_analise_resultadoSearch', Tbc_analise_resultadoSearch);

    Tbc_analise_resultadoSearch.$inject = ['$resource'];

    function Tbc_analise_resultadoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-analise-resultados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
