(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_fazendaSearch', Tbc_fazendaSearch);

    Tbc_fazendaSearch.$inject = ['$resource'];

    function Tbc_fazendaSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-fazendas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
