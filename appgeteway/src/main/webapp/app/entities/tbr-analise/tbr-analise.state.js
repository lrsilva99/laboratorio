(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbr-analise', {
            parent: 'entity',
            url: '/tbr-analise/{id_Amostra}/edit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_analise.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-analise/tbr-analises.html',
                    controller: 'Tbr_analiseController',
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
                        id_Amostra:  $stateParams.id_Amostra
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_analise');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbr-analise-detail', {
            parent: 'entity',
            url: '/tbr-analise/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbr_analise.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbr-analise/tbr-analise-detail.html',
                    controller: 'Tbr_analiseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbr_analise');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbr_analise', function($stateParams, Tbr_analise) {
                    return Tbr_analise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbr-analise',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbr-analise-detail.edit', {
            parent: 'tbr-analise-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise/tbr-analise-dialog.html',
                    controller: 'Tbr_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_analise', function(Tbr_analise) {
                            return Tbr_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-analise.new', {
            parent: 'tbr-analise',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise/tbr-analise-dialog.html',
                    controller: 'Tbr_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
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
                                observacao: null,
                                forma_cadastro: null,
                                anotacao_status: null,
                                directiva_data_atu: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbr-analise', null, { reload: 'tbr-analise' });
                }, function() {
                    $state.go('tbr-analise');
                });
            }]
        })
        .state('tbr-analise.edit', {
            parent: 'tbr-analise',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise/tbr-analise-dialog.html',
                    controller: 'Tbr_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbr_analise', function(Tbr_analise) {
                            return Tbr_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-analise', null, { reload: 'tbr-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbr-analise.delete', {
            parent: 'tbr-analise',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbr-analise/tbr-analise-delete-dialog.html',
                    controller: 'Tbr_analiseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbr_analise', function(Tbr_analise) {
                            return Tbr_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbr-analise', {tbr_amostra_id: vm.tbr_amostra_id}, { reload: 'tbr-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
