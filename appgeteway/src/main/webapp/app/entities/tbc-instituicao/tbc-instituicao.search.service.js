(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_instituicaoSearch', Tbc_instituicaoSearch);

    Tbc_instituicaoSearch.$inject = ['$resource'];

    function Tbc_instituicaoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-instituicaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
