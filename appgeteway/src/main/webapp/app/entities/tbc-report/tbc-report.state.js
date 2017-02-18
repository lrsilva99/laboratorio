(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tbc-report', {
            parent: 'entity',
            url: '/tbc-report',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_report.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-report/tbc-reports.html',
                    controller: 'Tbc_reportController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_report');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tbc-report-detail', {
            parent: 'entity',
            url: '/tbc-report/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appgetewayApp.tbc_report.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tbc-report/tbc-report-detail.html',
                    controller: 'Tbc_reportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tbc_report');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tbc_report', function($stateParams, Tbc_report) {
                    return Tbc_report.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tbc-report',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tbc-report-detail.edit', {
            parent: 'tbc-report-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-report/tbc-report-dialog.html',
                    controller: 'Tbc_reportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_report', function(Tbc_report) {
                            return Tbc_report.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-report.new', {
            parent: 'tbc-report',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-report/tbc-report-dialog.html',
                    controller: 'Tbc_reportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                arquivo: null,
                                removido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tbc-report', null, { reload: 'tbc-report' });
                }, function() {
                    $state.go('tbc-report');
                });
            }]
        })
        .state('tbc-report.edit', {
            parent: 'tbc-report',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-report/tbc-report-dialog.html',
                    controller: 'Tbc_reportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tbc_report', function(Tbc_report) {
                            return Tbc_report.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-report', null, { reload: 'tbc-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tbc-report.delete', {
            parent: 'tbc-report',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tbc-report/tbc-report-delete-dialog.html',
                    controller: 'Tbc_reportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tbc_report', function(Tbc_report) {
                            return Tbc_report.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tbc-report', null, { reload: 'tbc-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
