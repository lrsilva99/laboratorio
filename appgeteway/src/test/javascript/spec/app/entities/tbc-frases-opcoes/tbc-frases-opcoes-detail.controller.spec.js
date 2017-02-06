'use strict';

describe('Controller Tests', function() {

    describe('Tbc_frases_opcoes Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_frases_opcoes, MockTbc_frases;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_frases_opcoes = jasmine.createSpy('MockTbc_frases_opcoes');
            MockTbc_frases = jasmine.createSpy('MockTbc_frases');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_frases_opcoes': MockTbc_frases_opcoes,
                'Tbc_frases': MockTbc_frases
            };
            createController = function() {
                $injector.get('$controller')("Tbc_frases_opcoesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_frases_opcoesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
