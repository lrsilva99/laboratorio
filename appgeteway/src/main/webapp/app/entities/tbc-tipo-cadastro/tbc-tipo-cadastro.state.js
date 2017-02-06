(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-tipo-cadastro', {
            parent: 'entity',
            url: '/tbc-tipo-cadastro?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_tipo_cadastro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastros.html',
                    controller: 'Tbc_tipo_cadastroController',
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
                    $translatePartialLoader.addPart('tbc_tipo_cadastro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-tipo-cadastro-detail', {
            parent: 'entity',
            url: '/tbc-tipo-cadastro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_tipo_cadastro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastro-detail.html',
                    controller: 'Tbc_tipo_cadastroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_tipo_cadastro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_tipo_cadastro', function($stateParams, Tbc_tipo_cadastro) {
                    return Tbc_tipo_cadastro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-tipo-cadastro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-tipo-cadastro-detail.edit', {
            parent: 'tbc-tipo-cadastro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastro-dialog.html',
                    controller: 'Tbc_tipo_cadastroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_tipo_cadastro', function(Tbc_tipo_cadastro) {
                            return Tbc_tipo_cadastro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-tipo-cadastro.new', {
            parent: 'tbc-tipo-cadastro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastro-dialog.html',
                    controller: 'Tbc_tipo_cadastroDialogController',
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
                    $state.go('tbc-tipo-cadastro', null, { reload: 'tbc-tipo-cadastro' });
                }, function() {
                    $state.go('tbc-tipo-cadastro');
                });
            }]
        })
        .state('tbc-tipo-cadastro.edit', {
            parent: 'tbc-tipo-cadastro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastro-dialog.html',
                    controller: 'Tbc_tipo_cadastroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_tipo_cadastro', function(Tbc_tipo_cadastro) {
                            return Tbc_tipo_cadastro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-tipo-cadastro', null, { reload: 'tbc-tipo-cadastro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-tipo-cadastro.delete', {
            parent: 'tbc-tipo-cadastro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-cadastro/tbc-tipo-cadastro-delete-dialog.html',
                    controller: 'Tbc_tipo_cadastroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_tipo_cadastro', function(Tbc_tipo_cadastro) {
                            return Tbc_tipo_cadastro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-tipo-cadastro', null, { reload: 'tbc-tipo-cadastro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
