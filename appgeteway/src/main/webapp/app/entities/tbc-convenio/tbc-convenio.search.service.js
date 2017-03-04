(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_convenioSearch', Tbc_convenioSearch);

    Tbc_convenioSearch.$inject = ['$resource'];

    function Tbc_convenioSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-convenios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
