(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-matriz', {
            parent: 'entity',
            url: '/tbc-matriz?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_matriz.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-matriz/tbc-matrizs.html',
                    controller: 'Tbc_matrizController',
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
                    $translatePartialLoader.addPart('tbc_matriz');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-matriz-detail', {
            parent: 'entity',
            url: '/tbc-matriz/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_matriz.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-matriz/tbc-matriz-detail.html',
                    controller: 'Tbc_matrizDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_matriz');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_matriz', function($stateParams, Tbc_matriz) {
                    return Tbc_matriz.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-matriz',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-matriz-detail.edit', {
            parent: 'tbc-matriz-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-matriz/tbc-matriz-dialog.html',
                    controller: 'Tbc_matrizDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_matriz', function(Tbc_matriz) {
                            return Tbc_matriz.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-matriz.new', {
            parent: 'tbc-matriz',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-matriz/tbc-matriz-dialog.html',
                    controller: 'Tbc_matrizDialogController',
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
                    $state.go('tbc-matriz', null, { reload: 'tbc-matriz' });
                }, function() {
                    $state.go('tbc-matriz');
                });
            }]
        })
        .state('tbc-matriz.edit', {
            parent: 'tbc-matriz',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-matriz/tbc-matriz-dialog.html',
                    controller: 'Tbc_matrizDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_matriz', function(Tbc_matriz) {
                            return Tbc_matriz.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-matriz', null, { reload: 'tbc-matriz' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-matriz.delete', {
            parent: 'tbc-matriz',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-matriz/tbc-matriz-delete-dialog.html',
                    controller: 'Tbc_matrizDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_matriz', function(Tbc_matriz) {
                            return Tbc_matriz.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-matriz', null, { reload: 'tbc-matriz' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
