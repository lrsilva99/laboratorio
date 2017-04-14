(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_analise_resultadoController', Tbr_analise_resultadoController)
        .directive('stringToNumber', function() {
        return {
        require: 'ngModel',
        link: function(scope, element, attrs, ngModel) {
                             ngModel.$parsers.push(function(value) {
                               return '' + value;
                             });
                             ngModel.$formatters.push(function(value) {
                               return parseFloat(value);
                             });
                           }
                         };
                         });



    Tbr_analise_resultadoController.$inject = ['$stateParams', '$scope', '$state', 'DataUtils', 'Tbr_analise_resultado', 'Tbr_analise_resultadoSearch', 'Tbr_amostra','Tbc_status'];

    function Tbr_analise_resultadoController ($stateParams, $scope, $state, DataUtils, Tbr_analise_resultado, Tbr_analise_resultadoSearch, Tbr_amostra, Tbc_status) {
        var vm = this;

        vm.tbr_analise_resultados = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.ultimoGrupoAnalise = '';
        vm.ultimoTbc_analise_id = '';
        vm.tbc_grupo_analise_nome;
        vm.detalhes = false;
        vm.tbr_amostra_id = $stateParams.tbr_amostra_id;
        vm.tbr_amostra;
        vm.save = save;
        vm.tbc_statuAutoriza;
        vm.tbc_statuReativa;


        loadAll();
        //Verificar a observação digitada na análise
        vm.validaObservacao = function ( grupo_tbr_analise_resultado, tbr_analise_resultado){
            grupo_tbr_analise_resultado.analise = tbr_analise_resultado.tbr_analise_id;
         }
         vm.ativaObservacao = function (show){
         return !show
         }
          vm.agrupaGrupoAnalise = function (tbc_analises_id){
                 var resultado = true;
                     if (vm.ultimoTbc_analise_id == tbc_analises_id){
                         resultado  = false;
                     }else{
                         vm.ultimoTbc_analise_id = tbc_analises_id;
                     resultado = true;

                     }
                     return  resultado;
                 }

        //Adicionar conteudo select Ng-0ptions
         vm.opcaoNgOptions = function  getSplit (str){
                  return str.split("@");
          };

        //Verificar se o tipo do campo é de cálculo e retornar o resuldo quando pertinente
         vm.verificarCalculo = function (tbr_analise_resultados_grupo, index){
            var index = index + 1;
             while (tbr_analise_resultados_grupo.tbr_analise_resultados [index]) {
                    if (tbr_analise_resultados_grupo.tbr_analise_resultados[index].tbc_analises_componente.tbc_tipo_campo.nome == 'Calculo'){
                        //Armazenar a expressão
                        var expressao = tbr_analise_resultados_grupo.tbr_analise_resultados[index].tbc_analises_componente.configuracao;
                        //Criar arrei com os parametro de configuração
                        var formula = expressao.split("@");
                        var contformular = 0;
                        while (formula[contformular]){
                                if (formula[contformular] != '('){
                                    //Recuperar o valor digitado
                                    var  valorDigitado = getValuerArray(formula[contformular],tbr_analise_resultados_grupo.tbr_analise_resultados)
                                    //Subistituir o nome do campo na formula pelo valor digitado
                                    expressao = expressao.replace(formula[contformular], valorDigitado);
                                     contformular = contformular + 1;
                                    }else
                                     contformular = contformular + 1;
                            }
                        //Removendo o parametro @ da fórmula
                        expressao = expressao.split("@").join("");;
                        tbr_analise_resultados_grupo.tbr_analise_resultados[index].resultado = parseFloat(eval(expressao.toString()).toFixed(2));
                        index = index + 1;
                     }else
                        index  = 1000;
                      }
         }



        //Recuperar o valor digitado no nome do componente informado
         function getValuerArray (nomecampo, tbr_analise_resultados){
                  var i = 0
                  var valor = nomecampo;
                  while (tbr_analise_resultados[i]) {
                        if (tbr_analise_resultados[i].analises_componente == nomecampo){
                            valor = tbr_analise_resultados[i].resultado;
                            i = 99999;
                        }else
                        i = i + 1;

              }
              return valor;

         }
        function loadAll() {
        if ($stateParams.tbr_amostra_id !== "undefined"){
            if ($stateParams.tbr_amostra_id !== ""){
                vm.tbr_amostra = Tbr_amostra.get({id : vm.tbr_amostra_id});
                Tbr_analise_resultado.query({tbr_amostra_id: $stateParams.tbr_amostra_id },function(result) {
                                             vm.tbr_analise_resultados = result;
                                             vm.searchQuery = null;
                });
            }
            }
        }


        //Retornar o nome do grupo de análise
        vm.validaGrupoAnalise = function (tbc_analises){
        var resultado = "";
            if (vm.ultimoGrupoAnalise == tbc_analises.tbc_grupo_analise.nome){
                resultado  = "";
            }else{
            vm.ultimoGrupoAnalise = tbc_analises.tbc_grupo_analise.nome;
            resultado = tbc_analises.nome;

            }
            return  resultado;
        }

        //Atualiza legenta funcionalidade Status
        vm.validaAtivado = function (tbr_analise_resultado) {
        if (tbr_analise_resultado.tbc_status.nome == 'Autorizado')
            return "appgetewayApp.tbr_analise_resultado.reativar"
        else
            return "appgetewayApp.tbr_analise_resultado.liberar"
        }

        //Tornar disponível o botão reativar amostra
        vm.ngshowTbc_status = function (grupo_tbr_analise_resultado){
        var ngshow = false;
        if (vm.detalhes == true){
            var index = 0;
            while (grupo_tbr_analise_resultado.tbr_analise_resultados [index]) {
                  if (grupo_tbr_analise_resultado.tbr_analise_resultados[index].id != null){
                    ngshow = true;
                    index = index + 1;
                  }else{
                  ngshow = false;
                  index = 10000000;
                  }

            }
        }
        return ngshow;

        }
        //Atualizar o o campos de status para operação de liberação e reativação de resultado
        vm.atualizaCamposStatus = function (){
            if ( typeof vm.tbc_statuAutoriza == "undefined"){
                vm.tbc_statuAutoriza  = Tbc_status.get({id: 9});
                vm.tbc_statuReativa  = Tbc_status.get({id: 10});
            }
        }

         vm.liberaReativaAnalise = function (tbr_analise_resultados_grupo, selecionado){


         var index = 0;
         while (tbr_analise_resultados_grupo.tbr_analise_resultados [index]) {
               if (selecionado || typeof selecionado == "undefined"){
                  if (tbr_analise_resultados_grupo.tbr_analise_resultados [index].tbc_status.id == 1 )
                     tbr_analise_resultados_grupo.tbr_analise_resultados [index].tbc_status = vm.tbc_statuReativa;
                  else
                  tbr_analise_resultados_grupo.tbr_analise_resultados [index].tbc_status = vm.tbc_statuAutoriza;
              }
               else
               tbr_analise_resultados_grupo.tbr_analise_resultados [index].tbc_status =     tbr_analise_resultados_grupo.tbr_analise_resultados [index].tbc_status_ultimo;
           index = index + 1;
         }
         if (typeof selecionado === "undefined")
                     selecionado = false;
         return !selecionado;
    }
            //Regra para desativar de componetes para entrada de resultado
        vm.desableComponentEntradaResultado = function (status){
                var enable = false;
            if (status.id == 1){
                enable = true;
            }else
                enable = false;
            return enable;
        }



        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            vm.tbr_amostra_id = vm.searchQuery;

            $state.go('tbr-analise-resultado', {tbr_amostra_id: vm.tbr_amostra_id}, { reload: 'tbr-analise-resultado' });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();

        }

            function save () {
            vm.isSaving = true;
            Tbr_analise_resultado.update(vm.tbr_analise_resultados,onSaveSuccess,onSaveError);


        }
         function onSaveSuccess (result) {
                    $scope.$emit('appgetewayApp:tbr_analise_resultadoUpdate', result);
                    vm.isSaving = false;
                    $state.go('tbr-analise-resultado', {tbr_amostra_id: vm.tbr_amostra_id}, { reload: 'tbr-analise-resultado' });
                }
                 function onSaveError () {
                    vm.isSaving = false;
                }
        }


})();
