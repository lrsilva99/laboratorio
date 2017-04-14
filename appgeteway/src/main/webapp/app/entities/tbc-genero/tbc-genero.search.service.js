(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_generoSearch', Tbc_generoSearch);

    Tbc_generoSearch.$inject = ['$resource'];

    function Tbc_generoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-generos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
