(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_plano_teste_analiseSearch', Tbc_plano_teste_analiseSearch);

    Tbc_plano_teste_analiseSearch.$inject = ['$resource'];

    function Tbc_plano_teste_analiseSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-plano-teste-analises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
