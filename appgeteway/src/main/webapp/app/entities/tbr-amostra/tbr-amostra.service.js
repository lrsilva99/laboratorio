(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbr_amostra', Tbr_amostra);

    Tbr_amostra.$inject = ['$resource', 'DateUtils'];

    function Tbr_amostra ($resource, DateUtils) {
        var resourceUrl =  'lims/' + 'api/tbr-amostras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.recebimento_rec_data = DateUtils.convertDateTimeFromServer(data.recebimento_rec_data);
                        data.coleta_data = DateUtils.convertDateTimeFromServer(data.coleta_data);
                        data.autorizado_data = DateUtils.convertDateTimeFromServer(data.autorizado_data);
                        data.cancelado_data = DateUtils.convertDateTimeFromServer(data.cancelado_data);
                        data.suspenso_data = DateUtils.convertDateTimeFromServer(data.suspenso_data);
                        data.rejeitado_data = DateUtils.convertDateTimeFromServer(data.rejeitado_data);
                        data.disponivel_data = DateUtils.convertDateTimeFromServer(data.disponivel_data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
