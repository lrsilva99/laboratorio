(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-plano-teste-analise', {
            parent: 'entity',
            url: '/tbc-plano-teste-analise?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_plano_teste_analise.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analises.html',
                    controller: 'Tbc_plano_teste_analiseController',
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
                    $translatePartialLoader.addPart('tbc_plano_teste_analise');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-plano-teste-analise-detail', {
            parent: 'entity',
            url: '/tbc-plano-teste-analise/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_plano_teste_analise.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analise-detail.html',
                    controller: 'Tbc_plano_teste_analiseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_plano_teste_analise');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_plano_teste_analise', function($stateParams, Tbc_plano_teste_analise) {
                    return Tbc_plano_teste_analise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-plano-teste-analise',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-plano-teste-analise-detail.edit', {
            parent: 'tbc-plano-teste-analise-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analise-dialog.html',
                    controller: 'Tbc_plano_teste_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_plano_teste_analise', function(Tbc_plano_teste_analise) {
                            return Tbc_plano_teste_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-plano-teste-analise.new', {
            parent: 'tbc-plano-teste-analise',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analise-dialog.html',
                    controller: 'Tbc_plano_teste_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ordem: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-plano-teste-analise', null, { reload: 'tbc-plano-teste-analise' });
                }, function() {
                    $state.go('tbc-plano-teste-analise');
                });
            }]
        })
        .state('tbc-plano-teste-analise.edit', {
            parent: 'tbc-plano-teste-analise',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analise-dialog.html',
                    controller: 'Tbc_plano_teste_analiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_plano_teste_analise', function(Tbc_plano_teste_analise) {
                            return Tbc_plano_teste_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-plano-teste-analise', null, { reload: 'tbc-plano-teste-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-plano-teste-analise.delete', {
            parent: 'tbc-plano-teste-analise',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-plano-teste-analise/tbc-plano-teste-analise-delete-dialog.html',
                    controller: 'Tbc_plano_teste_analiseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_plano_teste_analise', function(Tbc_plano_teste_analise) {
                            return Tbc_plano_teste_analise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-plano-teste-analise', null, { reload: 'tbc-plano-teste-analise' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
