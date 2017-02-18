(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-lab-tercerizado', {
            parent: 'entity',
            url: '/tbc-lab-tercerizado?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_lab_tercerizado.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizados.html',
                    controller: 'Tbc_lab_tercerizadoController',
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
                    $translatePartialLoader.addPart('tbc_lab_tercerizado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-lab-tercerizado-detail', {
            parent: 'entity',
            url: '/tbc-lab-tercerizado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_lab_tercerizado.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizado-detail.html',
                    controller: 'Tbc_lab_tercerizadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_lab_tercerizado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_lab_tercerizado', function($stateParams, Tbc_lab_tercerizado) {
                    return Tbc_lab_tercerizado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-lab-tercerizado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-lab-tercerizado-detail.edit', {
            parent: 'tbc-lab-tercerizado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizado-dialog.html',
                    controller: 'Tbc_lab_tercerizadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_lab_tercerizado', function(Tbc_lab_tercerizado) {
                            return Tbc_lab_tercerizado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-lab-tercerizado.new', {
            parent: 'tbc-lab-tercerizado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizado-dialog.html',
                    controller: 'Tbc_lab_tercerizadoDialogController',
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
                    $state.go('tbc-lab-tercerizado', null, { reload: 'tbc-lab-tercerizado' });
                }, function() {
                    $state.go('tbc-lab-tercerizado');
                });
            }]
        })
        .state('tbc-lab-tercerizado.edit', {
            parent: 'tbc-lab-tercerizado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizado-dialog.html',
                    controller: 'Tbc_lab_tercerizadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_lab_tercerizado', function(Tbc_lab_tercerizado) {
                            return Tbc_lab_tercerizado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-lab-tercerizado', null, { reload: 'tbc-lab-tercerizado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-lab-tercerizado.delete', {
            parent: 'tbc-lab-tercerizado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-lab-tercerizado/tbc-lab-tercerizado-delete-dialog.html',
                    controller: 'Tbc_lab_tercerizadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_lab_tercerizado', function(Tbc_lab_tercerizado) {
                            return Tbc_lab_tercerizado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-lab-tercerizado', null, { reload: 'tbc-lab-tercerizado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
