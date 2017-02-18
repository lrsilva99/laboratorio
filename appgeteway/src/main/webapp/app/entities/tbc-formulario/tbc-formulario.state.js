(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-formulario', {
            parent: 'entity',
            url: '/tbc-formulario?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_formulario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-formulario/tbc-formularios.html',
                    controller: 'Tbc_formularioController',
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
                    $translatePartialLoader.addPart('tbc_formulario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-formulario-detail', {
            parent: 'entity',
            url: '/tbc-formulario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_formulario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-formulario/tbc-formulario-detail.html',
                    controller: 'Tbc_formularioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_formulario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_formulario', function($stateParams, Tbc_formulario) {
                    return Tbc_formulario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-formulario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-formulario-detail.edit', {
            parent: 'tbc-formulario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario/tbc-formulario-dialog.html',
                    controller: 'Tbc_formularioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_formulario', function(Tbc_formulario) {
                            return Tbc_formulario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-formulario.new', {
            parent: 'tbc-formulario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario/tbc-formulario-dialog.html',
                    controller: 'Tbc_formularioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                metodo: null,
                                descricao: null,
                                diasliberacao: null,
                                removido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario', null, { reload: 'tbc-formulario' });
                }, function() {
                    $state.go('tbc-formulario');
                });
            }]
        })
        .state('tbc-formulario.edit', {
            parent: 'tbc-formulario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario/tbc-formulario-dialog.html',
                    controller: 'Tbc_formularioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_formulario', function(Tbc_formulario) {
                            return Tbc_formulario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario', null, { reload: 'tbc-formulario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-formulario.delete', {
            parent: 'tbc-formulario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario/tbc-formulario-delete-dialog.html',
                    controller: 'Tbc_formularioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_formulario', function(Tbc_formulario) {
                            return Tbc_formulario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario', null, { reload: 'tbc-formulario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
