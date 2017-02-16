(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-frases-opcoes', {
            parent: 'entity',
              url: '/{idfrases}/editpage&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_frases_opcoes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes.html',
                    controller: 'Tbc_frases_opcoesController',
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
                        search: $stateParams.search,
                        idfrases: $stateParams.idfrases
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_frases_opcoes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-frases-opcoes-detail', {
            parent: 'entity',
            url: '/tbc-frases-opcoes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_frases_opcoes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes-detail.html',
                    controller: 'Tbc_frases_opcoesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_frases_opcoes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_frases_opcoes', function($stateParams, Tbc_frases_opcoes) {
                    return Tbc_frases_opcoes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-frases-opcoes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-frases-opcoes-detail.edit', {
            parent: 'tbc-frases-opcoes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes-dialog.html',
                    controller: 'Tbc_frases_opcoesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_frases_opcoes', function(Tbc_frases_opcoes) {
                            return Tbc_frases_opcoes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-frases-opcoes.new', {
            parent: 'tbc-frases-opcoes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes-dialog.html',
                    controller: 'Tbc_frases_opcoesDialogController',
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
                    $state.go('tbc-frases-opcoes', null, { reload: 'tbc-frases-opcoes' });
                }, function() {
                    $state.go('tbc-frases-opcoes');
                });
            }]
        })
        .state('tbc-frases-opcoes.edit', {
            parent: 'tbc-frases-opcoes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes-dialog.html',
                    controller: 'Tbc_frases_opcoesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_frases_opcoes', function(Tbc_frases_opcoes) {
                            return Tbc_frases_opcoes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-frases-opcoes', null, { reload: 'tbc-frases-opcoes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-frases-opcoes.delete', {
            parent: 'tbc-frases-opcoes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes-delete-dialog.html',
                    controller: 'Tbc_frases_opcoesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_frases_opcoes', function(Tbc_frases_opcoes) {
                            return Tbc_frases_opcoes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-frases-opcoes', null, { reload: 'tbc-frases-opcoes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-frases-opcoes.abrir', {
                     parent: 'entity',
                     url: '/{id}/Abrir',
                     data: {
                         authorities: ['ROLE_USER'],
                         pageTitle: 'appgetewayApp.tbc_frases_opcoes.home.title'
                     },
                     views: {
                         'content@': {
                             templateUrl: 'app/entities/tbc-frases-opcoes/tbc-frases-opcoes.html',
                             controller: 'Tbc_frases_opcoesController',
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
                         search: null,
                          idFrases: null
                     },
                     resolve: {
                         pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                             return {
                                 page: PaginationUtil.parsePage($stateParams.page),
                                 sort: $stateParams.sort,
                                 predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                 ascending: PaginationUtil.parseAscending($stateParams.sort),
                                 search: $stateParams.search,
                                 idFrases: $stateParams.id
                             };
                         }],
                         translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                             $translatePartialLoader.addPart('tbc_frases_opcoes');
                             $translatePartialLoader.addPart('global');
                             return $translate.refresh();
                         }]
                     }
                 }

        );
    }

})();
