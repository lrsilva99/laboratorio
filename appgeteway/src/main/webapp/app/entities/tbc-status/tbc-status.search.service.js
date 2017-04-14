(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_statusSearch', Tbc_statusSearch);

    Tbc_statusSearch.$inject = ['$resource'];

    function Tbc_statusSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
