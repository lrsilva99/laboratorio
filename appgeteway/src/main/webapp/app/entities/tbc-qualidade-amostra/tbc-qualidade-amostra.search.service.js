(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_qualidade_amostraSearch', Tbc_qualidade_amostraSearch);

    Tbc_qualidade_amostraSearch.$inject = ['$resource'];

    function Tbc_qualidade_amostraSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-qualidade-amostras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
