(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-fazenda', {
            parent: 'grandesanimais',
            url: '/tbc-fazenda?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_fazenda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazendas.html',
                    controller: 'Tbc_fazendaController',
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
                    $translatePartialLoader.addPart('tbc_fazenda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-fazenda-detail', {
            parent: 'grandesanimais',
            url: '/tbc-fazenda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_fazenda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazenda-detail.html',
                    controller: 'Tbc_fazendaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_fazenda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_fazenda', function($stateParams, Tbc_fazenda) {
                    return Tbc_fazenda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-fazenda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-fazenda-detail.edit', {
            parent: 'tbc-fazenda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazenda-dialog.html',
                    controller: 'Tbc_fazendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_fazenda', function(Tbc_fazenda) {
                            return Tbc_fazenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-fazenda.new', {
            parent: 'tbc-fazenda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazenda-dialog.html',
                    controller: 'Tbc_fazendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-fazenda', null, { reload: 'tbc-fazenda' });
                }, function() {
                    $state.go('tbc-fazenda');
                });
            }]
        })
        .state('tbc-fazenda.edit', {
            parent: 'tbc-fazenda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazenda-dialog.html',
                    controller: 'Tbc_fazendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_fazenda', function(Tbc_fazenda) {
                            return Tbc_fazenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-fazenda', null, { reload: 'tbc-fazenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-fazenda.delete', {
            parent: 'tbc-fazenda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-fazenda/tbc-fazenda-delete-dialog.html',
                    controller: 'Tbc_fazendaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_fazenda', function(Tbc_fazenda) {
                            return Tbc_fazenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-fazenda', null, { reload: 'tbc-fazenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
