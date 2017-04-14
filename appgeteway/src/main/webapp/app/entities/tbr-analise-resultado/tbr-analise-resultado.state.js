(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbr-analise-resultado', {
            parent: 'entity',
            url: '/tbr-analise-resultado/{tbr_amostra_id}/todos',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_analise_resultado.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultados.html',
                    controller: 'Tbr_analise_resultadoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_analise_resultado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbr-analise-resultado-detail', {
            parent: 'entity',
            url: '/tbr-analise-resultado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_analise_resultado.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultado-detail.html',
                    controller: 'Tbr_analise_resultadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_analise_resultado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbr_analise_resultado', function($stateParams, Tbr_analise_resultado) {
                    return Tbr_analise_resultado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbr-analise-resultado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbr-analise-resultado-detail.edit', {
            parent: 'tbr-analise-resultado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultado-dialog.html',
                    controller: 'Tbr_analise_resultadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_analise_resultado', function(Tbr_analise_resultado) {
                            return Tbr_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-analise-resultado.new', {
            parent: 'tbr-analise-resultado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultado-dialog.html',
                    controller: 'Tbr_analise_resultadoDialogController',
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
                                tbr_amostra_id: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbr-analise-resultado', null, { reload: 'tbr-analise-resultado' });
                }, function() {
                    $state.go('tbr-analise-resultado');
                });
            }]
        })
        .state('tbr-analise-resultado.edit', {
            parent: 'tbr-analise-resultado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultado-dialog.html',
                    controller: 'Tbr_analise_resultadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_analise_resultado', function(Tbr_analise_resultado) {
                            return Tbr_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-analise-resultado', null, { reload: 'tbr-analise-resultado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-analise-resultado.delete', {
            parent: 'tbr-analise-resultado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise-resultado/tbr-analise-resultado-delete-dialog.html',
                    controller: 'Tbr_analise_resultadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbr_analise_resultado', function(Tbr_analise_resultado) {
                            return Tbr_analise_resultado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-analise-resultado', {tbr_amostra_id: $stateParams.tbr_amostra_id}, { reload: 'tbr-analise-resultado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
