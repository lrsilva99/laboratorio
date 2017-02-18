(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-tipo-campo', {
            parent: 'entity',
            url: '/tbc-tipo-campo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_tipo_campo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campos.html',
                    controller: 'Tbc_tipo_campoController',
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
                    $translatePartialLoader.addPart('tbc_tipo_campo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-tipo-campo-detail', {
            parent: 'entity',
            url: '/tbc-tipo-campo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_tipo_campo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campo-detail.html',
                    controller: 'Tbc_tipo_campoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_tipo_campo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_tipo_campo', function($stateParams, Tbc_tipo_campo) {
                    return Tbc_tipo_campo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-tipo-campo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-tipo-campo-detail.edit', {
            parent: 'tbc-tipo-campo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campo-dialog.html',
                    controller: 'Tbc_tipo_campoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_tipo_campo', function(Tbc_tipo_campo) {
                            return Tbc_tipo_campo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-tipo-campo.new', {
            parent: 'tbc-tipo-campo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campo-dialog.html',
                    controller: 'Tbc_tipo_campoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-tipo-campo', null, { reload: 'tbc-tipo-campo' });
                }, function() {
                    $state.go('tbc-tipo-campo');
                });
            }]
        })
        .state('tbc-tipo-campo.edit', {
            parent: 'tbc-tipo-campo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campo-dialog.html',
                    controller: 'Tbc_tipo_campoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_tipo_campo', function(Tbc_tipo_campo) {
                            return Tbc_tipo_campo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-tipo-campo', null, { reload: 'tbc-tipo-campo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-tipo-campo.delete', {
            parent: 'tbc-tipo-campo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-tipo-campo/tbc-tipo-campo-delete-dialog.html',
                    controller: 'Tbc_tipo_campoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_tipo_campo', function(Tbc_tipo_campo) {
                            return Tbc_tipo_campo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-tipo-campo', null, { reload: 'tbc-tipo-campo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
