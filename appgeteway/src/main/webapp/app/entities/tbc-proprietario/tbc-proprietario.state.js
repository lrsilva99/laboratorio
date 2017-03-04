(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-proprietario', {
            parent: 'grandesanimais',
            url: '/tbc-proprietario?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_proprietario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietarios.html',
                    controller: 'Tbc_proprietarioController',
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
                    $translatePartialLoader.addPart('tbc_proprietario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-proprietario-detail', {
            parent: 'grandesanimais',
            url: '/tbc-proprietario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_proprietario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietario-detail.html',
                    controller: 'Tbc_proprietarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_proprietario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_proprietario', function($stateParams, Tbc_proprietario) {
                    return Tbc_proprietario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-proprietario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-proprietario-detail.edit', {
            parent: 'tbc-proprietario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietario-dialog.html',
                    controller: 'Tbc_proprietarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_proprietario', function(Tbc_proprietario) {
                            return Tbc_proprietario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-proprietario.new', {
            parent: 'tbc-proprietario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietario-dialog.html',
                    controller: 'Tbc_proprietarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                endereco: null,
                                cpf_cnpj: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-proprietario', null, { reload: 'tbc-proprietario' });
                }, function() {
                    $state.go('tbc-proprietario');
                });
            }]
        })
        .state('tbc-proprietario.edit', {
            parent: 'tbc-proprietario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietario-dialog.html',
                    controller: 'Tbc_proprietarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_proprietario', function(Tbc_proprietario) {
                            return Tbc_proprietario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-proprietario', null, { reload: 'tbc-proprietario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-proprietario.delete', {
            parent: 'tbc-proprietario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-proprietario/tbc-proprietario-delete-dialog.html',
                    controller: 'Tbc_proprietarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_proprietario', function(Tbc_proprietario) {
                            return Tbc_proprietario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-proprietario', null, { reload: 'tbc-proprietario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
