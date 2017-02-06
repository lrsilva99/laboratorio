(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_tipo_cadastroSearch', Tbc_tipo_cadastroSearch);

    Tbc_tipo_cadastroSearch.$inject = ['$resource'];

    function Tbc_tipo_cadastroSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-tipo-cadastros/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
