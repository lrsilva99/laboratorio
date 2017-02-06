(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_frasesSearch', Tbc_frasesSearch);

    Tbc_frasesSearch.$inject = ['$resource'];

    function Tbc_frasesSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-frases/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
