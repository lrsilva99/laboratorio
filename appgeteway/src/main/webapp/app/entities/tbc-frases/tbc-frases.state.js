(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-frases', {
            parent: 'entity',
            url: '/tbc-frases?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_frases.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-frases/tbc-frases.html',
                    controller: 'Tbc_frasesController',
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
                    $translatePartialLoader.addPart('tbc_frases');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-frases-detail', {
            parent: 'entity',
            url: '/tbc-frases/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_frases.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-frases/tbc-frases-detail.html',
                    controller: 'Tbc_frasesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_frases');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_frases', function($stateParams, Tbc_frases) {
                    return Tbc_frases.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-frases',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-frases-detail.edit', {
            parent: 'tbc-frases-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases/tbc-frases-dialog.html',
                    controller: 'Tbc_frasesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_frases', function(Tbc_frases) {
                            return Tbc_frases.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-frases.new', {
            parent: 'tbc-frases',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases/tbc-frases-dialog.html',
                    controller: 'Tbc_frasesDialogController',
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
                    $state.go('tbc-frases', null, { reload: 'tbc-frases' });
                }, function() {
                    $state.go('tbc-frases');
                });
            }]
        })
        .state('tbc-frases.edit', {
            parent: 'tbc-frases',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases/tbc-frases-dialog.html',
                    controller: 'Tbc_frasesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_frases', function(Tbc_frases) {
                            return Tbc_frases.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-frases', null, { reload: 'tbc-frases' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-frases.delete', {
            parent: 'tbc-frases',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases/tbc-frases-delete-dialog.html',
                    controller: 'Tbc_frasesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_frases', function(Tbc_frases) {
                            return Tbc_frases.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-frases', null, { reload: 'tbc-frases' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
