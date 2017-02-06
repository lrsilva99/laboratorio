(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-instituicao', {
            parent: 'laboratorio',
            url: '/tbc-instituicao?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_instituicao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicaos.html',
                    controller: 'Tbc_instituicaoController',
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
                    $translatePartialLoader.addPart('tbc_instituicao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-instituicao-detail', {
            parent: 'laboratorio',
            url: '/tbc-instituicao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_instituicao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicao-detail.html',
                    controller: 'Tbc_instituicaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_instituicao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_instituicao', function($stateParams, Tbc_instituicao) {
                    return Tbc_instituicao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-instituicao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-instituicao-detail.edit', {
            parent: 'tbc-instituicao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicao-dialog.html',
                    controller: 'Tbc_instituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_instituicao', function(Tbc_instituicao) {
                            return Tbc_instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-instituicao.new', {
            parent: 'tbc-instituicao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicao-dialog.html',
                    controller: 'Tbc_instituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                sigla: null,
                                uf: null,
                                endereco: null,
                                telefone: null,
                                removido: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-instituicao', null, { reload: 'tbc-instituicao' });
                }, function() {
                    $state.go('tbc-instituicao');
                });
            }]
        })
        .state('tbc-instituicao.edit', {
            parent: 'tbc-instituicao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicao-dialog.html',
                    controller: 'Tbc_instituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_instituicao', function(Tbc_instituicao) {
                            return Tbc_instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-instituicao', null, { reload: 'tbc-instituicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-instituicao.delete', {
            parent: 'tbc-instituicao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-instituicao/tbc-instituicao-delete-dialog.html',
                    controller: 'Tbc_instituicaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_instituicao', function(Tbc_instituicao) {
                            return Tbc_instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-instituicao', null, { reload: 'tbc-instituicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
