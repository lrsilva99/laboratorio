'use strict';

describe('Controller Tests', function() {

    describe('Tbc_frases Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_frases, MockTbc_instituicao, MockTbc_sub_grupo, MockTbc_frases_opcoes;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_frases = jasmine.createSpy('MockTbc_frases');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_sub_grupo = jasmine.createSpy('MockTbc_sub_grupo');
            MockTbc_frases_opcoes = jasmine.createSpy('MockTbc_frases_opcoes');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_frases': MockTbc_frases,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_sub_grupo': MockTbc_sub_grupo,
                'Tbc_frases_opcoes': MockTbc_frases_opcoes
            };
            createController = function() {
                $injector.get('$controller')("Tbc_frasesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_frasesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
