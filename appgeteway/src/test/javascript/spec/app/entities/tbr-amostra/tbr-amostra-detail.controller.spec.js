'use strict';

describe('Controller Tests', function() {

    describe('Tbr_amostra Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbr_amostra, MockTbc_especie, MockTbc_sub_grupo, MockTbc_status, MockTbc_cliente, MockTbc_proprietario, MockTbc_cooperativa, MockTbc_fazenda, MockTbc_forma_armazenamento, MockTbc_formulario, MockTbc_convenio, MockTbc_genero, MockTbc_qualidade_amostra, MockTbr_analise;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbr_amostra = jasmine.createSpy('MockTbr_amostra');
            MockTbc_especie = jasmine.createSpy('MockTbc_especie');
            MockTbc_sub_grupo = jasmine.createSpy('MockTbc_sub_grupo');
            MockTbc_status = jasmine.createSpy('MockTbc_status');
            MockTbc_cliente = jasmine.createSpy('MockTbc_cliente');
            MockTbc_proprietario = jasmine.createSpy('MockTbc_proprietario');
            MockTbc_cooperativa = jasmine.createSpy('MockTbc_cooperativa');
            MockTbc_fazenda = jasmine.createSpy('MockTbc_fazenda');
            MockTbc_forma_armazenamento = jasmine.createSpy('MockTbc_forma_armazenamento');
            MockTbc_formulario = jasmine.createSpy('MockTbc_formulario');
            MockTbc_convenio = jasmine.createSpy('MockTbc_convenio');
            MockTbc_genero = jasmine.createSpy('MockTbc_genero');
            MockTbc_qualidade_amostra = jasmine.createSpy('MockTbc_qualidade_amostra');
            MockTbr_analise = jasmine.createSpy('MockTbr_analise');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbr_amostra': MockTbr_amostra,
                'Tbc_especie': MockTbc_especie,
                'Tbc_sub_grupo': MockTbc_sub_grupo,
                'Tbc_status': MockTbc_status,
                'Tbc_cliente': MockTbc_cliente,
                'Tbc_proprietario': MockTbc_proprietario,
                'Tbc_cooperativa': MockTbc_cooperativa,
                'Tbc_fazenda': MockTbc_fazenda,
                'Tbc_forma_armazenamento': MockTbc_forma_armazenamento,
                'Tbc_formulario': MockTbc_formulario,
                'Tbc_convenio': MockTbc_convenio,
                'Tbc_genero': MockTbc_genero,
                'Tbc_qualidade_amostra': MockTbc_qualidade_amostra,
                'Tbr_analise': MockTbr_analise
            };
            createController = function() {
                $injector.get('$controller')("Tbr_amostraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbr_amostraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
