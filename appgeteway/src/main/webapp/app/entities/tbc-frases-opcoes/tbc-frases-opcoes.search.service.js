(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_frases_opcoesSearch', Tbc_frases_opcoesSearch);

    Tbc_frases_opcoesSearch.$inject = ['$resource'];

    function Tbc_frases_opcoesSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-frases-opcoes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
