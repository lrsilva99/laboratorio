(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbr_analise_resultado', Tbr_analise_resultado);

    Tbr_analise_resultado.$inject = ['$resource', 'DateUtils'];

    function Tbr_analise_resultado ($resource, DateUtils) {
        var resourceUrl =  'lims/' + 'api/tbr-analise-resultados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.autorizado_data = DateUtils.convertDateTimeFromServer(data.autorizado_data);
                        data.cancelado_data = DateUtils.convertDateTimeFromServer(data.cancelado_data);
                        data.suspenso_data = DateUtils.convertDateTimeFromServer(data.suspenso_data);
                        data.rejeitado_data = DateUtils.convertDateTimeFromServer(data.rejeitado_data);
                        data.disponivel_data = DateUtils.convertDateTimeFromServer(data.disponivel_data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' , isArray: true }
        });
    }
})();
