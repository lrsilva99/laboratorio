(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-genero', {
            parent: 'entity',
            url: '/tbc-genero?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_genero.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-genero/tbc-generos.html',
                    controller: 'Tbc_generoController',
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
                    $translatePartialLoader.addPart('tbc_genero');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-genero-detail', {
            parent: 'entity',
            url: '/tbc-genero/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_genero.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-genero/tbc-genero-detail.html',
                    controller: 'Tbc_generoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_genero');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_genero', function($stateParams, Tbc_genero) {
                    return Tbc_genero.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-genero',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-genero-detail.edit', {
            parent: 'tbc-genero-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-genero/tbc-genero-dialog.html',
                    controller: 'Tbc_generoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_genero', function(Tbc_genero) {
                            return Tbc_genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-genero.new', {
            parent: 'tbc-genero',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-genero/tbc-genero-dialog.html',
                    controller: 'Tbc_generoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                remover: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-genero', null, { reload: 'tbc-genero' });
                }, function() {
                    $state.go('tbc-genero');
                });
            }]
        })
        .state('tbc-genero.edit', {
            parent: 'tbc-genero',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-genero/tbc-genero-dialog.html',
                    controller: 'Tbc_generoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_genero', function(Tbc_genero) {
                            return Tbc_genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-genero', null, { reload: 'tbc-genero' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-genero.delete', {
            parent: 'tbc-genero',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-genero/tbc-genero-delete-dialog.html',
                    controller: 'Tbc_generoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_genero', function(Tbc_genero) {
                            return Tbc_genero.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-genero', null, { reload: 'tbc-genero' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
