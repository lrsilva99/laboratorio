'use strict';

describe('Controller Tests', function() {

    describe('Tbc_grupo_analise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_grupo_analise, MockTbc_instituicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_grupo_analise = jasmine.createSpy('MockTbc_grupo_analise');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_grupo_analise': MockTbc_grupo_analise,
                'Tbc_instituicao': MockTbc_instituicao
            };
            createController = function() {
                $injector.get('$controller')("Tbc_grupo_analiseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_grupo_analiseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
