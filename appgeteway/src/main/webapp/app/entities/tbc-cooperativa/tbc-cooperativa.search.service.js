(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_cooperativaSearch', Tbc_cooperativaSearch);

    Tbc_cooperativaSearch.$inject = ['$resource'];

    function Tbc_cooperativaSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-cooperativas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
