(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-qualidade-amostra', {
            parent: 'entity',
            url: '/tbc-qualidade-amostra?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_qualidade_amostra.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostras.html',
                    controller: 'Tbc_qualidade_amostraController',
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
                    $translatePartialLoader.addPart('tbc_qualidade_amostra');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-qualidade-amostra-detail', {
            parent: 'entity',
            url: '/tbc-qualidade-amostra/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_qualidade_amostra.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostra-detail.html',
                    controller: 'Tbc_qualidade_amostraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_qualidade_amostra');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_qualidade_amostra', function($stateParams, Tbc_qualidade_amostra) {
                    return Tbc_qualidade_amostra.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-qualidade-amostra',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-qualidade-amostra-detail.edit', {
            parent: 'tbc-qualidade-amostra-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostra-dialog.html',
                    controller: 'Tbc_qualidade_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_qualidade_amostra', function(Tbc_qualidade_amostra) {
                            return Tbc_qualidade_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-qualidade-amostra.new', {
            parent: 'tbc-qualidade-amostra',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostra-dialog.html',
                    controller: 'Tbc_qualidade_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                remover: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-qualidade-amostra', null, { reload: 'tbc-qualidade-amostra' });
                }, function() {
                    $state.go('tbc-qualidade-amostra');
                });
            }]
        })
        .state('tbc-qualidade-amostra.edit', {
            parent: 'tbc-qualidade-amostra',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostra-dialog.html',
                    controller: 'Tbc_qualidade_amostraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_qualidade_amostra', function(Tbc_qualidade_amostra) {
                            return Tbc_qualidade_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-qualidade-amostra', null, { reload: 'tbc-qualidade-amostra' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-qualidade-amostra.delete', {
            parent: 'tbc-qualidade-amostra',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-qualidade-amostra/tbc-qualidade-amostra-delete-dialog.html',
                    controller: 'Tbc_qualidade_amostraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_qualidade_amostra', function(Tbc_qualidade_amostra) {
                            return Tbc_qualidade_amostra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-qualidade-amostra', null, { reload: 'tbc-qualidade-amostra' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
