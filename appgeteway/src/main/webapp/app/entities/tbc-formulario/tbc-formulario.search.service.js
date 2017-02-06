(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_formularioSearch', Tbc_formularioSearch);

    Tbc_formularioSearch.$inject = ['$resource'];

    function Tbc_formularioSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-formularios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
