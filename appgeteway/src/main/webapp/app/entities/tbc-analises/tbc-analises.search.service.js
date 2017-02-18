(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_analisesSearch', Tbc_analisesSearch);

    Tbc_analisesSearch.$inject = ['$resource'];

    function Tbc_analisesSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-analises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
