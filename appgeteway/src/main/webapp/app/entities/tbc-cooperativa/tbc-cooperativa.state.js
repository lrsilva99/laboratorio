(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-cooperativa', {
            parent: 'grandesanimais',
            url: '/tbc-cooperativa?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_cooperativa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativas.html',
                    controller: 'Tbc_cooperativaController',
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
                    $translatePartialLoader.addPart('tbc_cooperativa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-cooperativa-detail', {
            parent: 'grandesanimais',
            url: '/tbc-cooperativa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_cooperativa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativa-detail.html',
                    controller: 'Tbc_cooperativaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_cooperativa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_cooperativa', function($stateParams, Tbc_cooperativa) {
                    return Tbc_cooperativa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-cooperativa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-cooperativa-detail.edit', {
            parent: 'tbc-cooperativa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativa-dialog.html',
                    controller: 'Tbc_cooperativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_cooperativa', function(Tbc_cooperativa) {
                            return Tbc_cooperativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-cooperativa.new', {
            parent: 'tbc-cooperativa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativa-dialog.html',
                    controller: 'Tbc_cooperativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                removido: null,
                                email: null,
                                telefone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-cooperativa', null, { reload: 'tbc-cooperativa' });
                }, function() {
                    $state.go('tbc-cooperativa');
                });
            }]
        })
        .state('tbc-cooperativa.edit', {
            parent: 'tbc-cooperativa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativa-dialog.html',
                    controller: 'Tbc_cooperativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_cooperativa', function(Tbc_cooperativa) {
                            return Tbc_cooperativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-cooperativa', null, { reload: 'tbc-cooperativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-cooperativa.delete', {
            parent: 'tbc-cooperativa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-cooperativa/tbc-cooperativa-delete-dialog.html',
                    controller: 'Tbc_cooperativaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_cooperativa', function(Tbc_cooperativa) {
                            return Tbc_cooperativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-cooperativa', null, { reload: 'tbc-cooperativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
