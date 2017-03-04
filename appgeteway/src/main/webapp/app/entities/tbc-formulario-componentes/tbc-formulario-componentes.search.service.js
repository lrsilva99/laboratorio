(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .factory('Tbc_formulario_componentesSearch', Tbc_formulario_componentesSearch);

    Tbc_formulario_componentesSearch.$inject = ['$resource'];

    function Tbc_formulario_componentesSearch($resource) {
        var resourceUrl =  'lims/' + 'api/_search/tbc-formulario-componentes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
