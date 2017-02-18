(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-plano-teste', {
            parent: 'entity',
            url: '/tbc-plano-teste?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_plano_teste.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-testes.html',
                    controller: 'Tbc_plano_testeController',
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
                    $translatePartialLoader.addPart('tbc_plano_teste');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-plano-teste-detail', {
            parent: 'entity',
            url: '/tbc-plano-teste/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_plano_teste.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-teste-detail.html',
                    controller: 'Tbc_plano_testeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_plano_teste');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_plano_teste', function($stateParams, Tbc_plano_teste) {
                    return Tbc_plano_teste.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-plano-teste',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-plano-teste-detail.edit', {
            parent: 'tbc-plano-teste-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-teste-dialog.html',
                    controller: 'Tbc_plano_testeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_plano_teste', function(Tbc_plano_teste) {
                            return Tbc_plano_teste.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-plano-teste.new', {
            parent: 'tbc-plano-teste',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-teste-dialog.html',
                    controller: 'Tbc_plano_testeDialogController',
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
                    $state.go('tbc-plano-teste', null, { reload: 'tbc-plano-teste' });
                }, function() {
                    $state.go('tbc-plano-teste');
                });
            }]
        })
        .state('tbc-plano-teste.edit', {
            parent: 'tbc-plano-teste',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-teste-dialog.html',
                    controller: 'Tbc_plano_testeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_plano_teste', function(Tbc_plano_teste) {
                            return Tbc_plano_teste.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-plano-teste', null, { reload: 'tbc-plano-teste' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-plano-teste.delete', {
            parent: 'tbc-plano-teste',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste/tbc-plano-teste-delete-dialog.html',
                    controller: 'Tbc_plano_testeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_plano_teste', function(Tbc_plano_teste) {
                            return Tbc_plano_teste.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-plano-teste', null, { reload: 'tbc-plano-teste' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
