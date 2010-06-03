/**
 * GjME - Game JavaME
 * A Framework to build JavaME games quickly.
 * Copyright (c) 2009-2010 Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 * -----------------------------------------------------------------------------
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is
 *   Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>.
 *
 * The Initial Developer of the Original Code is
 *   Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>.
 * Portions created by Initial Developer are Copyright (C) 2009
 * Initial Developer. All Rights Reserved.
 *
 * Contributor(s): None
 *
 * Alternatively, the contents of this file may be used under the terms
 * of the New BSD license (the "New BSD License"), in which case the
 * provisions of New BSD License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the New BSD License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the New BSD License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the New BSD License.
 */
package br.com.upzone.gjme.personagem.acao;

import br.com.upzone.gjme.personagem.GjME_Personagem;

/**
 * Esta classe é serve como base para a criação de todos os personagens do game.
 *
 * Neste contexto, tudo o que o personagem executa é representado por uma ação.
 * Exemplo de ações:
 *   Ficar parado;
 *   Andar;
 *   Pular;
 *   Cair (de um pulo ou de um precipicio).
 *
 * Ao configurar uma ação, também é necessário configurar os frames que serão
 * utilizados pela ação e se estes frames deverão ser repetidos assim que chegar
 * ao fim da lista, ou se a animação deve congelar no último frame.
 * Algumas ações podem necessidade de uma pré ou pós ação que também é definido
 * na criação de uma ação.
 * Exemplo de pré ações:
 *   Agachar para tomar impulso para um pulo;
 *   O personagem se virar ao trocar sua direção de movimento.
 * Exemplo de pós ações:
 *   Cair ao tomar um golpe de um inimigo.
 *
 * Uma ação possui três estados de execução:
 *   Não iniciada: A ação ainda não foi iniciada;
 *   Em execução: A ação está sendo execudata;
 *   Finalizado: A ação foi finalizada;
 * Geralmente, quando uma ação for avaliada como finalizada, serão executadas as
 * rotinas de finalização (como execução de outra ação) e ação será definida
 * com parada para poder ser executada novamente em outra ocasião.
 * @abstract
 */
public abstract class GjME_Acao {

  // -- Continuidade de animação -----------------------------------------------
  /**
   * Indica que a animação deve ser reiniciada quando chegar ao fim da lista de frames.
   */
  public final static boolean ANIMACAO_CONTINUA = true;
  /**
   * Indica que a animação deve ser congelada quando chegar ao fim da lista de frames.
   */
  public final static boolean ANIMACAO_NAO_CONTINUA = false;

  // -- Estados de execução ----------------------------------------------------
  /**
   * Indica que a execução da ação ainda não foi iniciada.
   */
  public final static int ACAO_NAO_INICIADA = -1;
  /**
   * Indica que a execução da ação está acontecendo.
   */
  public final static int ACAO_EM_EXECUCAO = 0;
  /**
   * Indica que a execução da ação já chegou ao fim.
   */
  public final static int ACAO_FINALIZADA = 1;

  // -- Propriedades -----------------------------------------------------------
  /**
   * Indica o tipo de animação da ação. Por default é animação contínua.
   * @see GjME_Acao.ANIMACAO_CONTINUA
   * @see GjME_Acao.ANIMACAO_NAO_CONTINUA
   */
  protected boolean tipoAnimacao = GjME_Acao.ANIMACAO_CONTINUA;

  /**
   * Indica o estado de execução da ação.
   * @see GjME_Acao.ACAO_NAO_INICIADA
   * @see GjME_Acao.ACAO_EM_EXECUCAO
   * @see GjME_Acao.ACAO_FINALIZADA
   */
  protected int estadoExecucao = GjME_Acao.ACAO_NAO_INICIADA;

  /**
   * Seqüência de frames da animação correspondente a esta ação.
   */
  protected int[] iarFrames;

  /**
   * ID desta ação.
   */
  protected int IDAcao;
  /**
   * ID da ação que deve ser executada antes desta ação.
   *
   * Exemplo de pré-ação:
   *   Abaixar para impulcionar um pulo.
   */
  protected int IDPreAcao = GjME_Personagem.ACAO_INVALIDA;
  /**
   * ID da ação que deve ser executada após a execução desta ação.
   * Exemplo de pós-acao:
   *   Agachar para amortecer o fim de um pulo.
   */
  protected int IDPosAcao = GjME_Personagem.ACAO_INVALIDA;

  // -- Métodos de acesso ------------------------------------------------------
  /**
   * Define o tipo de animação da ação.
   * @param bTipoAnimação Valores válidos: GjME_Acao.ANIMACAO_CONTINUA e GjME_Acao.ANIMACAO_NAO_CONTINUA
   * @return Referência para a instância de GjME_Acao.
   */
  public GjME_Acao defineTipoAnimacao(boolean bTipoAnimacao) {
    this.tipoAnimacao = bTipoAnimacao;
    return this;
  }

  /**
   * Define o estado de execução da ação de fora da classe.
   * @param iEstadoExecucao Valores válidos: GjME_Acao.ACAO_NAO_INICIADA, GjME_Acao.ACAO_EM_EXECUCAO e GjME_Acao.ACAO_FINALIZADA.
   */
  public GjME_Acao defineEstadoExecucao(int iEstadoExecucao) {
    this.estadoExecucao = iEstadoExecucao;
    return this;
  }

  /**
   * Indica se a animação da ação é continua ou se congela no final da lista de frames.
   * @return Verdadeiro ou falso.
   */
  public boolean animacaoContinua() { return this.tipoAnimacao; }

  /**
   * Indica se a execução da ação já foi finalizada.
   * @return Verdadeiro ou falso.
   */
  public boolean acaoFinalizada() {
    return (GjME_Acao.ACAO_FINALIZADA == this.estadoExecucao);
  }

  /**
   * Indica se a execução da ação já foi iniciada.
   * @return Verdadeiro ou falso.
   */
  public boolean acaoIniciada() {
    return (GjME_Acao.ACAO_NAO_INICIADA != this.estadoExecucao);
  }

  /**
   * Define o ID de identificação da ação.
   * @param ID ID de identificação.
   */
  public void defineIDAcao(int ID) { this.IDAcao = ID; }
  /**
   * Retorna o ID da ação.
   * @return ID da ação.
   */
  public int retornaIDAcao() { return this.IDAcao; }
  /**
   * Define uma pré-ação para ser executada antes desta ação.
   * @param IDAcao O identificador da ação que será utilizada como pré-ação.
   * @return Referência para esta ação.
   */
  public GjME_Acao definePreAcao(int IDAcao) {
    this.IDPreAcao = IDAcao;
    return this;
  }
  /**
   * Retorna o ID da pré-ação.
   * @return ID pré-ação.
   */
  public int retornaPreAcao() { return this.IDPreAcao; }
  /**
   * Define uma pós-ação para ser executada após esta ação.
   * @param IDAcao O identificador da ação que será utilizada como pós-ação.
   * @return Referência para esta ação.
   */
  public GjME_Acao definePosAcao(int IDAcao) {
    this.IDPosAcao = IDAcao;
    return this;
  }
  /**
   * Retorna o ID da pós-ação.
   * @return ID pós-ação.
   */
  public int retornaPosAcao() { return this.IDPosAcao; }
  /**
   * Indica se existe uma pré-ação.
   * @return Verdadeiro ou falso.
   */
  public boolean temPreAcao() {
    return GjME_Personagem.ACAO_INVALIDA == this.IDPreAcao;
  }
  /**
   * Indica se existe uma pós-ação.
   * @return Verdadeiro ou falso.
   */
  public boolean temPosAcao() {
    return GjME_Personagem.ACAO_INVALIDA == this.IDPosAcao;
  }

  /**
   * Retorna a lista de frames utilizadas na animação da ação.
   * @return Lista de frames.
   */
  public int[] retornaFrames() { return this.iarFrames; }

  // -- Construtores -----------------------------------------------------------
  /**
   * Cria uma nova ação com uma seqüência de frames pré-determinada.
   * @param  iarFrames Seqüência pré-determinada de frames para a animação.
   */
  protected GjME_Acao(int[] iarFrames) {
    this.iarFrames = iarFrames;
  }
  /**
   * Cria uma nova ação com um intervalo de frames.
   * @param iFrameInicial Frame inicial do intervalo de frames;
   * @param iFrameFinal Frame final do intervalo de frames;
   */
  public GjME_Acao(int iFrameInicial, int iFrameFinal) {
    this.iarFrames = new int[iFrameFinal - iFrameInicial + 1];
    int j = 0;
    for (int i = iFrameInicial; i <= iFrameFinal; i++) {
      this.iarFrames[j++] = i;
    }
  }

  // -- Métodos abstratos ------------------------------------------------------
  /**
   * As subclasses devem implementar este método para a execução de alguma ação extram.
   *
   * As ações extras podem ser ajustes rotinas do tipo:
   *   Ajustes de ponto de vida;
   *   Ajustes de pontuação (score);
   *   Criação de projéteis.
   * @param prg Referência para instância do personagem que sofrerá alterações.
   */
  public abstract void executar(GjME_Personagem prg);
}