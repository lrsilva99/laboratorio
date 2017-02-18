(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-cliente', {
            parent: 'entity',
            url: '/tbc-cliente?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_cliente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-cliente/tbc-clientes.html',
                    controller: 'Tbc_clienteController',
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
                    $translatePartialLoader.addPart('tbc_cliente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-cliente-detail', {
            parent: 'entity',
            url: '/tbc-cliente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_cliente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-cliente/tbc-cliente-detail.html',
                    controller: 'Tbc_clienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_cliente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_cliente', function($stateParams, Tbc_cliente) {
                    return Tbc_cliente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-cliente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-cliente-detail.edit', {
            parent: 'tbc-cliente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cliente/tbc-cliente-dialog.html',
                    controller: 'Tbc_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_cliente', function(Tbc_cliente) {
                            return Tbc_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-cliente.new', {
            parent: 'tbc-cliente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cliente/tbc-cliente-dialog.html',
                    controller: 'Tbc_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                cep: null,
                                endereco: null,
                                email: null,
                                imprimir: null,
                                enviar_email: null,
                                directiva_data_atu: null,
                                cpf_cnpj: null,
                                codigo_cliente: null,
                                removido: null,
                                bairro: null,
                                bloquear: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-cliente', null, { reload: 'tbc-cliente' });
                }, function() {
                    $state.go('tbc-cliente');
                });
            }]
        })
        .state('tbc-cliente.edit', {
            parent: 'tbc-cliente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cliente/tbc-cliente-dialog.html',
                    controller: 'Tbc_clienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_cliente', function(Tbc_cliente) {
                            return Tbc_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-cliente', null, { reload: 'tbc-cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-cliente.delete', {
            parent: 'tbc-cliente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cliente/tbc-cliente-delete-dialog.html',
                    controller: 'Tbc_clienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_cliente', function(Tbc_cliente) {
                            return Tbc_cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-cliente', null, { reload: 'tbc-cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
