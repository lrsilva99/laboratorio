(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbr-amostra', {
            parent: 'entity',
            url: '/tbr-amostra?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_amostra.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostras.html',
                    controller: 'Tbr_amostraController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_amostra');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbr-amostra-detail', {
            parent: 'entity',
            url: '/tbr-amostra/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_amostra.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostra-detail.html',
                    controller: 'Tbr_amostraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_amostra');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbr_amostra', function($stateParams, Tbr_amostra) {
                    return Tbr_amostra.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbr-amostra',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbr-amostra-detail.edit', {
            parent: 'tbr-amostra-detail',
            url: '{tbc_formulario_id}/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostra-dialog.html',
                    controller: 'Tbr_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_amostra', function(Tbr_amostra) {
                            return Tbr_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-amostra.new', {
            parent: 'tbr-amostra',
            url: '/{tbc_formulario_id}/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostra-dialog.html',
                    controller: 'Tbr_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                urgencia: null,
                                proprietario: null,
                                resp_colheita: null,
                                req_texto: null,
                                historico_clinico: null,
                                numero_for: null,
                                recebimento_rec_data: null,
                                suspeita_clinica: null,
                                coleta_data: null,
                                sexo: null,
                                raca: null,
                                idade: null,
                                identificao_amostra: null,
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
                                plano_teste: null,
                                plano_teste_a: null,
                                plano_teste_b: null,
                                plano_teste_c: null,
                                plano_teste_d: null,
                                plano_teste_e: null,
                                plano_teste_f: null,
                                numero_etiqueta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbr-amostra', null, { reload: 'tbr-amostra' });
                }, function() {
                    $state.go('tbr-amostra');
                });
            }]
        })
        .state('tbr-amostra.edit', {
            parent: 'tbr-amostra',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostra-dialog.html',
                    controller: 'Tbr_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_amostra', function(Tbr_amostra) {
                            return Tbr_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-amostra', null, { reload: 'tbr-amostra' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-amostra.delete', {
            parent: 'tbr-amostra',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-amostra/tbr-amostra-delete-dialog.html',
                    controller: 'Tbr_amostraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbr_amostra', function(Tbr_amostra) {
                            return Tbr_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-amostra', null, { reload: 'tbr-amostra' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
