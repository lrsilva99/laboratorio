(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbr_analise_resultadoSearch', Tbr_analise_resultadoSearch);

    Tbr_analise_resultadoSearch.$inject = ['$resource'];

    function Tbr_analise_resultadoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbr-analise-resultados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
