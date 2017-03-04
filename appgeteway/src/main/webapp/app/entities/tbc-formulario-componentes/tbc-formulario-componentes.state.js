(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-formulario-componentes', {
            parent: 'entity',
            url: '/tbc-formulario-componentes/{idFormulario}/?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_formulario_componentes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes.html',
                    controller: 'Tbc_formulario_componentesController',
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
                        search: $stateParams.search,
                        idFormulario: $stateParams.idFormulario
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_formulario_componentes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-formulario-componentes-detail', {
            parent: 'entity',
            url: '/tbc-formulario-componentes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_formulario_componentes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes-detail.html',
                    controller: 'Tbc_formulario_componentesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_formulario_componentes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_formulario_componentes', function($stateParams, Tbc_formulario_componentes) {
                    return Tbc_formulario_componentes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-formulario-componentes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-formulario-componentes-detail.edit', {
            parent: 'tbc-formulario-componentes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes-dialog.html',
                    controller: 'Tbc_formulario_componentesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_formulario_componentes', function(Tbc_formulario_componentes) {
                            return Tbc_formulario_componentes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-formulario-componentes.new', {
            parent: 'tbc-formulario-componentes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes-dialog.html',
                    controller: 'Tbc_formulario_componentesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                linha: null,
                                unidade_medida: null,
                                valor_padrao: null,
                                configuracao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario-componentes', null, { reload: 'tbc-formulario-componentes' });
                }, function() {
                    $state.go('tbc-formulario-componentes');
                });
            }]
        })
        .state('tbc-formulario-componentes.edit', {
            parent: 'tbc-formulario-componentes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes-dialog.html',
                    controller: 'Tbc_formulario_componentesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_formulario_componentes', function(Tbc_formulario_componentes) {
                            return Tbc_formulario_componentes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario-componentes', null, { reload: 'tbc-formulario-componentes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-formulario-componentes.delete', {
            parent: 'tbc-formulario-componentes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-formulario-componentes/tbc-formulario-componentes-delete-dialog.html',
                    controller: 'Tbc_formulario_componentesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_formulario_componentes', function(Tbc_formulario_componentes) {
                            return Tbc_formulario_componentes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-formulario-componentes', null, { reload: 'tbc-formulario-componentes' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
