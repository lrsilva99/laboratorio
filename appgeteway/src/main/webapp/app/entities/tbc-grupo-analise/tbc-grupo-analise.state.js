(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-grupo-analise', {
            parent: 'entity',
            url: '/tbc-grupo-analise?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_grupo_analise.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analises.html',
                    controller: 'Tbc_grupo_analiseController',
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
                    $translatePartialLoader.addPart('tbc_grupo_analise');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-grupo-analise-detail', {
            parent: 'entity',
            url: '/tbc-grupo-analise/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_grupo_analise.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analise-detail.html',
                    controller: 'Tbc_grupo_analiseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_grupo_analise');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_grupo_analise', function($stateParams, Tbc_grupo_analise) {
                    return Tbc_grupo_analise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-grupo-analise',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-grupo-analise-detail.edit', {
            parent: 'tbc-grupo-analise-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analise-dialog.html',
                    controller: 'Tbc_grupo_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_grupo_analise', function(Tbc_grupo_analise) {
                            return Tbc_grupo_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-grupo-analise.new', {
            parent: 'tbc-grupo-analise',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analise-dialog.html',
                    controller: 'Tbc_grupo_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-analise', null, { reload: 'tbc-grupo-analise' });
                }, function() {
                    $state.go('tbc-grupo-analise');
                });
            }]
        })
        .state('tbc-grupo-analise.edit', {
            parent: 'tbc-grupo-analise',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analise-dialog.html',
                    controller: 'Tbc_grupo_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_grupo_analise', function(Tbc_grupo_analise) {
                            return Tbc_grupo_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-analise', null, { reload: 'tbc-grupo-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-grupo-analise.delete', {
            parent: 'tbc-grupo-analise',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-analise/tbc-grupo-analise-delete-dialog.html',
                    controller: 'Tbc_grupo_analiseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_grupo_analise', function(Tbc_grupo_analise) {
                            return Tbc_grupo_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-analise', null, { reload: 'tbc-grupo-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
