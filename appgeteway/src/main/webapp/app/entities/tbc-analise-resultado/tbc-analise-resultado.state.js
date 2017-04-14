(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-analise-resultado', {
            parent: 'entity',
            url: '/tbc-analise-resultado',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analise_resultado.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultados.html',
                    controller: 'Tbc_analise_resultadoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_analise_resultado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-analise-resultado-detail', {
            parent: 'entity',
            url: '/tbc-analise-resultado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analise_resultado.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultado-detail.html',
                    controller: 'Tbc_analise_resultadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_analise_resultado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_analise_resultado', function($stateParams, Tbc_analise_resultado) {
                    return Tbc_analise_resultado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-analise-resultado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-analise-resultado-detail.edit', {
            parent: 'tbc-analise-resultado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultado-dialog.html',
                    controller: 'Tbc_analise_resultadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analise_resultado', function(Tbc_analise_resultado) {
                            return Tbc_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analise-resultado.new', {
            parent: 'tbc-analise-resultado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultado-dialog.html',
                    controller: 'Tbc_analise_resultadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                analises_componente: null,
                                resultado: null,
                                autorizado_por: null,
                                autorizado_data: null,
                                cancelado_por: null,
                                cancelado_data: null,
                                suspenso_por: null,
                                suspenso_data: null,
                                rejeitado_por: null,
                                rejeitado_data: null,
                                disponivel_por: null,
                                disponivel_data: null,
                                resultado_digitado: null,
                                unidade_medida: null,
                                tbr_analise_id: null,
                                resultado_texto: null,
                                repetido: null,
                                tbc_amostra_id: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-analise-resultado', null, { reload: 'tbc-analise-resultado' });
                }, function() {
                    $state.go('tbc-analise-resultado');
                });
            }]
        })
        .state('tbc-analise-resultado.edit', {
            parent: 'tbc-analise-resultado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultado-dialog.html',
                    controller: 'Tbc_analise_resultadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analise_resultado', function(Tbc_analise_resultado) {
                            return Tbc_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analise-resultado', null, { reload: 'tbc-analise-resultado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analise-resultado.delete', {
            parent: 'tbc-analise-resultado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analise-resultado/tbc-analise-resultado-delete-dialog.html',
                    controller: 'Tbc_analise_resultadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_analise_resultado', function(Tbc_analise_resultado) {
                            return Tbc_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analise-resultado', null, { reload: 'tbc-analise-resultado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
