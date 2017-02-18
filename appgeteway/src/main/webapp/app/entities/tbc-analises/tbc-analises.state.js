(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-analises', {
            parent: 'entity',
            url: '/tbc-analises?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analises.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analises/tbc-analises.html',
                    controller: 'Tbc_analisesController',
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
                    $translatePartialLoader.addPart('tbc_analises');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-analises-detail', {
            parent: 'entity',
            url: '/tbc-analises/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_analises.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-analises/tbc-analises-detail.html',
                    controller: 'Tbc_analisesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_analises');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_analises', function($stateParams, Tbc_analises) {
                    return Tbc_analises.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-analises',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-analises-detail.edit', {
            parent: 'tbc-analises-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises/tbc-analises-dialog.html',
                    controller: 'Tbc_analisesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analises', function(Tbc_analises) {
                            return Tbc_analises.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analises.new', {
            parent: 'tbc-analises',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises/tbc-analises-dialog.html',
                    controller: 'Tbc_analisesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                numerodias: null,
                                metpop: null,
                                removido: null,
                                tercerizado: null,
                                directiva_data_atu: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-analises', null, { reload: 'tbc-analises' });
                }, function() {
                    $state.go('tbc-analises');
                });
            }]
        })
        .state('tbc-analises.edit', {
            parent: 'tbc-analises',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises/tbc-analises-dialog.html',
                    controller: 'Tbc_analisesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_analises', function(Tbc_analises) {
                            return Tbc_analises.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analises', null, { reload: 'tbc-analises' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-analises.delete', {
            parent: 'tbc-analises',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-analises/tbc-analises-delete-dialog.html',
                    controller: 'Tbc_analisesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_analises', function(Tbc_analises) {
                            return Tbc_analises.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-analises', null, { reload: 'tbc-analises' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
