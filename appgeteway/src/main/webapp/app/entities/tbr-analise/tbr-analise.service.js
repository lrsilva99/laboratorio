(function() {
    'use strict';
    angular
        .module('appgetewayApp')
        .factory('Tbr_analise', Tbr_analise);

    Tbr_analise.$inject = ['$resource', 'DateUtils'];

    function Tbr_analise ($resource, DateUtils) {
        var resourceUrl =  'lims/' + 'api/tbr-analises/:id';

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
                        data.directiva_data_atu = DateUtils.convertLocalDateFromServer(data.directiva_data_atu);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.directiva_data_atu = DateUtils.convertLocalDateToServer(copy.directiva_data_atu);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.directiva_data_atu = DateUtils.convertLocalDateToServer(copy.directiva_data_atu);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
