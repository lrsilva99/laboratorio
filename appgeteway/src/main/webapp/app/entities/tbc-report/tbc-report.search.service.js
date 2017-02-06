(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_reportSearch', Tbc_reportSearch);

    Tbc_reportSearch.$inject = ['$resource'];

    function Tbc_reportSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-reports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
