(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-grupo-cliente', {
            parent: 'laboratorio',
            url: '/tbc-grupo-cliente?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_grupo_cliente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-clientes.html',
                    controller: 'Tbc_grupo_clienteController',
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
                    $translatePartialLoader.addPart('tbc_grupo_cliente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-grupo-cliente-detail', {
            parent: 'laboratorio',
            url: '/tbc-grupo-cliente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_grupo_cliente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-cliente-detail.html',
                    controller: 'Tbc_grupo_clienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_grupo_cliente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_grupo_cliente', function($stateParams, Tbc_grupo_cliente) {
                    return Tbc_grupo_cliente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-grupo-cliente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-grupo-cliente-detail.edit', {
            parent: 'tbc-grupo-cliente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-cliente-dialog.html',
                    controller: 'Tbc_grupo_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_grupo_cliente', function(Tbc_grupo_cliente) {
                            return Tbc_grupo_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-grupo-cliente.new', {
            parent: 'tbc-grupo-cliente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-cliente-dialog.html',
                    controller: 'Tbc_grupo_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                responsavel: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-cliente', null, { reload: 'tbc-grupo-cliente' });
                }, function() {
                    $state.go('tbc-grupo-cliente');
                });
            }]
        })
        .state('tbc-grupo-cliente.edit', {
            parent: 'tbc-grupo-cliente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-cliente-dialog.html',
                    controller: 'Tbc_grupo_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_grupo_cliente', function(Tbc_grupo_cliente) {
                            return Tbc_grupo_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-cliente', null, { reload: 'tbc-grupo-cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-grupo-cliente.delete', {
            parent: 'tbc-grupo-cliente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-grupo-cliente/tbc-grupo-cliente-delete-dialog.html',
                    controller: 'Tbc_grupo_clienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_grupo_cliente', function(Tbc_grupo_cliente) {
                            return Tbc_grupo_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-grupo-cliente', null, { reload: 'tbc-grupo-cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
