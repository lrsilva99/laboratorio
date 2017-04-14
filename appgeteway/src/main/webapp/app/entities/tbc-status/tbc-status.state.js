(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-status', {
            parent: 'entity',
            url: '/tbc-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_status.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-status/tbc-statuses.html',
                    controller: 'Tbc_statusController',
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
                    $translatePartialLoader.addPart('tbc_status');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-status-detail', {
            parent: 'entity',
            url: '/tbc-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_status.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-status/tbc-status-detail.html',
                    controller: 'Tbc_statusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_status');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_status', function($stateParams, Tbc_status) {
                    return Tbc_status.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-status-detail.edit', {
            parent: 'tbc-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-status/tbc-status-dialog.html',
                    controller: 'Tbc_statusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_status', function(Tbc_status) {
                            return Tbc_status.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-status.new', {
            parent: 'tbc-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-status/tbc-status-dialog.html',
                    controller: 'Tbc_statusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                icone: null,
                                id: null,
                                cor: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-status', null, { reload: 'tbc-status' });
                }, function() {
                    $state.go('tbc-status');
                });
            }]
        })
        .state('tbc-status.edit', {
            parent: 'tbc-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-status/tbc-status-dialog.html',
                    controller: 'Tbc_statusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_status', function(Tbc_status) {
                            return Tbc_status.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-status', null, { reload: 'tbc-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-status.delete', {
            parent: 'tbc-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-status/tbc-status-delete-dialog.html',
                    controller: 'Tbc_statusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_status', function(Tbc_status) {
                            return Tbc_status.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-status', null, { reload: 'tbc-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
