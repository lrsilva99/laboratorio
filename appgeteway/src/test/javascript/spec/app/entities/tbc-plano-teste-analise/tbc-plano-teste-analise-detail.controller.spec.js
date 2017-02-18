'use strict';

describe('Controller Tests', function() {

    describe('Tbc_plano_teste_analise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_plano_teste_analise, MockTbc_plano_teste, MockTbc_analises;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_plano_teste_analise = jasmine.createSpy('MockTbc_plano_teste_analise');
            MockTbc_plano_teste = jasmine.createSpy('MockTbc_plano_teste');
            MockTbc_analises = jasmine.createSpy('MockTbc_analises');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_plano_teste_analise': MockTbc_plano_teste_analise,
                'Tbc_plano_teste': MockTbc_plano_teste,
                'Tbc_analises': MockTbc_analises
            };
            createController = function() {
                $injector.get('$controller')("Tbc_plano_teste_analiseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_plano_teste_analiseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
