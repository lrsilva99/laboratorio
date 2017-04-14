(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_forma_armazenamentoSearch', Tbc_forma_armazenamentoSearch);

    Tbc_forma_armazenamentoSearch.$inject = ['$resource'];

    function Tbc_forma_armazenamentoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-forma-armazenamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
