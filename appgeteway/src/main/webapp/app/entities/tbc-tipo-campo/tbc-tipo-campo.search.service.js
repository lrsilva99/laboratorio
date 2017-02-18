(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_tipo_campoSearch', Tbc_tipo_campoSearch);

    Tbc_tipo_campoSearch.$inject = ['$resource'];

    function Tbc_tipo_campoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-tipo-campos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
