(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_especieSearch', Tbc_especieSearch);

    Tbc_especieSearch.$inject = ['$resource'];

    function Tbc_especieSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-especies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
