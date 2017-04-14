(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-convenio', {
            parent: 'laboratorio',
            url: '/tbc-convenio?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_convenio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenios.html',
                    controller: 'Tbc_convenioController',
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
                    $translatePartialLoader.addPart('tbc_convenio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-convenio-detail', {
            parent: 'laboratorio',
            url: '/tbc-convenio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_convenio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenio-detail.html',
                    controller: 'Tbc_convenioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_convenio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_convenio', function($stateParams, Tbc_convenio) {
                    return Tbc_convenio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-convenio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-convenio-detail.edit', {
            parent: 'tbc-convenio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenio-dialog.html',
                    controller: 'Tbc_convenioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_convenio', function(Tbc_convenio) {
                            return Tbc_convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-convenio.new', {
            parent: 'tbc-convenio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenio-dialog.html',
                    controller: 'Tbc_convenioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                cnpj: null,
                                descricao: null,
                                removido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-convenio', null, { reload: 'tbc-convenio' });
                }, function() {
                    $state.go('tbc-convenio');
                });
            }]
        })
        .state('tbc-convenio.edit', {
            parent: 'tbc-convenio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenio-dialog.html',
                    controller: 'Tbc_convenioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_convenio', function(Tbc_convenio) {
                            return Tbc_convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-convenio', null, { reload: 'tbc-convenio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-convenio.delete', {
            parent: 'tbc-convenio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-convenio/tbc-convenio-delete-dialog.html',
                    controller: 'Tbc_convenioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_convenio', function(Tbc_convenio) {
                            return Tbc_convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-convenio', null, { reload: 'tbc-convenio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
