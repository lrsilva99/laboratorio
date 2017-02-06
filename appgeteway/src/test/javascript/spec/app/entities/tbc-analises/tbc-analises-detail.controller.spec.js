'use strict';

describe('Controller Tests', function() {

    describe('Tbc_analises Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_analises, MockTbc_sub_grupo, MockTbc_grupo_analise, MockTbc_tipo_cadastro, MockTbc_instituicao, MockTbc_report;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_analises = jasmine.createSpy('MockTbc_analises');
            MockTbc_sub_grupo = jasmine.createSpy('MockTbc_sub_grupo');
            MockTbc_grupo_analise = jasmine.createSpy('MockTbc_grupo_analise');
            MockTbc_tipo_cadastro = jasmine.createSpy('MockTbc_tipo_cadastro');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_report = jasmine.createSpy('MockTbc_report');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_analises': MockTbc_analises,
                'Tbc_sub_grupo': MockTbc_sub_grupo,
                'Tbc_grupo_analise': MockTbc_grupo_analise,
                'Tbc_tipo_cadastro': MockTbc_tipo_cadastro,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_report': MockTbc_report
            };
            createController = function() {
                $injector.get('$controller')("Tbc_analisesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_analisesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
