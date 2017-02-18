(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_grupo_analiseSearch', Tbc_grupo_analiseSearch);

    Tbc_grupo_analiseSearch.$inject = ['$resource'];

    function Tbc_grupo_analiseSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-grupo-analises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
