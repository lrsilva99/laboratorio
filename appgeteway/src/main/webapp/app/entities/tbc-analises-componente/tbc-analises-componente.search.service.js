(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_analises_componenteSearch', Tbc_analises_componenteSearch);

    Tbc_analises_componenteSearch.$inject = ['$resource'];

    function Tbc_analises_componenteSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-analises-componentes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
