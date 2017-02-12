(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_plano_testeSearch', Tbc_plano_testeSearch);

    Tbc_plano_testeSearch.$inject = ['$resource'];

    function Tbc_plano_testeSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-plano-testes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
