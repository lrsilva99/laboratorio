(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_sub_grupoSearch', Tbc_sub_grupoSearch);

    Tbc_sub_grupoSearch.$inject = ['$resource'];

    function Tbc_sub_grupoSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-sub-grupos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
