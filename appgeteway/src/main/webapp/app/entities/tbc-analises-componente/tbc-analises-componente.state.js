(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-analises-componente', {
            parent: 'laboratorio',
            url: '/tbc-analises-componente/{idAnalise}/edit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analises_componente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componentes.html',
                    controller: 'Tbc_analises_componenteController',
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
                        search: $stateParams.search,
                        idAnalise: $stateParams.idAnalise
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_analises_componente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-analises-componente-detail', {
            parent: 'entity',
            url: '/tbc-analises-componente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analises_componente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componente-detail.html',
                    controller: 'Tbc_analises_componenteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_analises_componente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_analises_componente', function($stateParams, Tbc_analises_componente) {
                    return Tbc_analises_componente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-analises-componente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-analises-componente-detail.edit', {
            parent: 'tbc-analises-componente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componente-dialog.html',
                    controller: 'Tbc_analises_componenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analises_componente', function(Tbc_analises_componente) {
                            return Tbc_analises_componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analises-componente.new', {
            parent: 'tbc-analises-componente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componente-dialog.html',
                    controller: 'Tbc_analises_componenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                linha: null,
                                unidade_medida: null,
                                valor_padrao: null,
                                configuracao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-analises-componente', null, { reload: 'tbc-analises-componente' });
                }, function() {
                    $state.go('tbc-analises-componente');
                });
            }]
        })
        .state('tbc-analises-componente.edit', {
            parent: 'tbc-analises-componente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componente-dialog.html',
                    controller: 'Tbc_analises_componenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analises_componente', function(Tbc_analises_componente) {
                            return Tbc_analises_componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analises-componente', null, { reload: 'tbc-analises-componente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analises-componente.delete', {
            parent: 'tbc-analises-componente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises-componente/tbc-analises-componente-delete-dialog.html',
                    controller: 'Tbc_analises_componenteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_analises_componente', function(Tbc_analises_componente) {
                            return Tbc_analises_componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analises-componente', null, { reload: 'tbc-analises-componente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
