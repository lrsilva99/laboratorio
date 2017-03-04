(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_proprietarioSearch', Tbc_proprietarioSearch);

    Tbc_proprietarioSearch.$inject = ['$resource'];

    function Tbc_proprietarioSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-proprietarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
