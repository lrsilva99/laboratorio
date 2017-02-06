(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-sub-grupo', {
            parent: 'laboratorio',
            url: '/tbc-sub-grupo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_sub_grupo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupos.html',
                    controller: 'Tbc_sub_grupoController',
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
                    $translatePartialLoader.addPart('tbc_sub_grupo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-sub-grupo-detail', {
            parent: 'laboratorio',
            url: '/tbc-sub-grupo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_sub_grupo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupo-detail.html',
                    controller: 'Tbc_sub_grupoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_sub_grupo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_sub_grupo', function($stateParams, Tbc_sub_grupo) {
                    return Tbc_sub_grupo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-sub-grupo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-sub-grupo-detail.edit', {
            parent: 'tbc-sub-grupo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupo-dialog.html',
                    controller: 'Tbc_sub_grupoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_sub_grupo', function(Tbc_sub_grupo) {
                            return Tbc_sub_grupo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-sub-grupo.new', {
            parent: 'tbc-sub-grupo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupo-dialog.html',
                    controller: 'Tbc_sub_grupoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                sigla: null,
                                removido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-sub-grupo', null, { reload: 'tbc-sub-grupo' });
                }, function() {
                    $state.go('tbc-sub-grupo');
                });
            }]
        })
        .state('tbc-sub-grupo.edit', {
            parent: 'tbc-sub-grupo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupo-dialog.html',
                    controller: 'Tbc_sub_grupoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_sub_grupo', function(Tbc_sub_grupo) {
                            return Tbc_sub_grupo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-sub-grupo', null, { reload: 'tbc-sub-grupo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-sub-grupo.delete', {
            parent: 'tbc-sub-grupo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-sub-grupo/tbc-sub-grupo-delete-dialog.html',
                    controller: 'Tbc_sub_grupoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_sub_grupo', function(Tbc_sub_grupo) {
                            return Tbc_sub_grupo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-sub-grupo', null, { reload: 'tbc-sub-grupo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
