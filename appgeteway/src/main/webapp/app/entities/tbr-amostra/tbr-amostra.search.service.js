(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbr_amostraSearch', Tbr_amostraSearch);

    Tbr_amostraSearch.$inject = ['$resource'];

    function Tbr_amostraSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbr-amostras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
