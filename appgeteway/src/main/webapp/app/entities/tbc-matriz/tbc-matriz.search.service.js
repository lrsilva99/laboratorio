(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_matrizSearch', Tbc_matrizSearch);

    Tbc_matrizSearch.$inject = ['$resource'];

    function Tbc_matrizSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-matrizs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
