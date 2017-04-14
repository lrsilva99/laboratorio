(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbr_analiseSearch', Tbr_analiseSearch);

    Tbr_analiseSearch.$inject = ['$resource'];

    function Tbr_analiseSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbr-analises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
