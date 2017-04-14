(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-forma-armazenamento', {
            parent: 'entity',
            url: '/tbc-forma-armazenamento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_forma_armazenamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamentos.html',
                    controller: 'Tbc_forma_armazenamentoController',
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
                    $translatePartialLoader.addPart('tbc_forma_armazenamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-forma-armazenamento-detail', {
            parent: 'entity',
            url: '/tbc-forma-armazenamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_forma_armazenamento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamento-detail.html',
                    controller: 'Tbc_forma_armazenamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_forma_armazenamento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_forma_armazenamento', function($stateParams, Tbc_forma_armazenamento) {
                    return Tbc_forma_armazenamento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-forma-armazenamento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-forma-armazenamento-detail.edit', {
            parent: 'tbc-forma-armazenamento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamento-dialog.html',
                    controller: 'Tbc_forma_armazenamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_forma_armazenamento', function(Tbc_forma_armazenamento) {
                            return Tbc_forma_armazenamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-forma-armazenamento.new', {
            parent: 'tbc-forma-armazenamento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamento-dialog.html',
                    controller: 'Tbc_forma_armazenamentoDialogController',
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
                    $state.go('tbc-forma-armazenamento', null, { reload: 'tbc-forma-armazenamento' });
                }, function() {
                    $state.go('tbc-forma-armazenamento');
                });
            }]
        })
        .state('tbc-forma-armazenamento.edit', {
            parent: 'tbc-forma-armazenamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamento-dialog.html',
                    controller: 'Tbc_forma_armazenamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_forma_armazenamento', function(Tbc_forma_armazenamento) {
                            return Tbc_forma_armazenamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-forma-armazenamento', null, { reload: 'tbc-forma-armazenamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-forma-armazenamento.delete', {
            parent: 'tbc-forma-armazenamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-forma-armazenamento/tbc-forma-armazenamento-delete-dialog.html',
                    controller: 'Tbc_forma_armazenamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_forma_armazenamento', function(Tbc_forma_armazenamento) {
                            return Tbc_forma_armazenamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-forma-armazenamento', null, { reload: 'tbc-forma-armazenamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
