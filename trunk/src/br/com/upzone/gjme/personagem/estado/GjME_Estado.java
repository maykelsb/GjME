/**
 * GjME - Game JavaME
 * A Framework to build JavaME games quickly.
 * Copyright (c) 2009 Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
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
 * of the New BSD license (the  "New BSD License"), in which case the
 * provisions of New BSD License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the New BSD License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the New BSD License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the New BSD License.
 */
package br.com.upzone.gjme.personagem.estado;

import br.com.upzone.gjme.personagem.GjME_Personagem;

/**
 * @TODO OS STATUS DOS PRÉ ESTADOS DEVEM SER REDEFINIDOS LOGO QUE O ESTADO PRINCIPAL
 * @TODO É REINICIADO / INICIADO
 * @abstract
 */
public abstract class GjME_Estado {

  public final static boolean TIP_EST_CONTINUO = true;
  public final static boolean TIP_EST_NAO_CONTINUO = false;
  
  protected boolean bTipoEstado = GjME_Estado.TIP_EST_CONTINUO;
  public boolean estadoContinuo() { return this.bTipoEstado; }
  public GjME_Estado defineContinuidade(boolean bContinuo) { this.bTipoEstado = bContinuo; return this; }

  /**
   * Indica que o estado ainda não iniciou seu ciclo de execução.
   */
  public static final int STS_EST_PARADO = -1;
  /**
   * Este status indica que o estado está em execução.
   */
  public static final int STS_EST_EM_EXECUCAO = 0;
  /**
   * Indica que o estado já chegou ao fim de sua execução.
   */
  public static final int STS_EST_FINALIZADO = 1;
  /**
   * Indica o status atual do Estado.
   * @see Estado.PARADO;
   * @see Estado.EM_EXECUCAO;
   * @see Estado.FINALIZADO;
   */
  protected int iStatus = GjME_Estado.STS_EST_PARADO;

  /**
   * Define um status para o estado.
   *
   * @param iStatus Novo status.
   * @see Estado.PARADO;
   * @see Estado.EM_EXECUCAO;
   * @see Estado.FINALIZADO;
   */
  public void setStatus(int iStatus) { this.iStatus = iStatus; }
  /**
   * Retorna o status atual do estado.
   *
   * @return Status do estado.
   * @see Estado.PARADO;
   * @see Estado.EM_EXECUCAO;
   * @see Estado.FINALIZADO;
   */
  public int getStatus() { return this.iStatus; }

  public boolean finalizado() {
    return (this.iStatus == GjME_Estado.STS_EST_FINALIZADO);
  }

  /**
   * ID de identificação do estado.
   * 
   * Este ID é utilizado para setar seqüências de estados, defindo este como
   * um pos estado, por exemplo.
   */
  private int iID;

  /**
   * Referência para o personagem a quem o estado pertence.
   *
   * @see br.com.upzone.gjme.personagem.Personagem
   */
  protected GjME_Personagem personagem;

  /**
   * Indica um estado que deve ser executado antes deste.
   */
  protected int iPreEstado = GjME_Personagem.EST_PERSON_INVALIDO;
  /**
   * Indica um estado que deve ser executado após este.
   */
  protected int iPosEstado = GjME_Personagem.EST_PERSON_INVALIDO;

  public boolean posEstadoValido() {
    return (iPosEstado != GjME_Personagem.EST_PERSON_INVALIDO);
  }

  /**
   * Array de frames da animação do estado.
   * 
   * Quando o estado é iniciado, deve-se setar este conjunto de frames como
   * os frames do sprite, de forma que possa ser chamado apenas o Personagem.nextFrame();
   *
   * @see Personagem.nextFrame();
   * @see Personagem.setFrameSequence();
   */
  protected int[] iarFrames;

  /**
   * Constroi um novo estado com uma seqüência irregular de frames.
   * 
   * @param psg Referência para o personagem dono do estado;
   * @param iarFrames Seqüência de identificadores dos frames da animação do estado. Ex: {7, 5, 3}
   */
  protected GjME_Estado(GjME_Personagem psg, int iID, int[] iarFrames) {
    this.personagem = psg;
    this.iarFrames = iarFrames;
    this.iID = iID;
  }

  /**
   * Constroi um novo estado para um personagem com uma seqüência regular de frames.
   * 
   * @param psg Referência para o personagem dono do estado;
   * @param iFrameInicial O identificador do frame inicial da animação;
   * @param iFrameFinal O identificador do frame final da animação;
   */
  protected GjME_Estado(GjME_Personagem psg, int iID, int iFrameInicial, int iFrameFinal) {
    this.iarFrames = new int[iFrameFinal - iFrameInicial + 1];
    int j = 0;
    for (int i = iFrameInicial; i <= iFrameFinal; i++) {
      this.iarFrames[j++] = i;
    }
    this.personagem = psg;
    this.iID = iID;
  }

  public int[] getFrames() {
    return this.iarFrames;
  }

  /**
   * Retorna referência para um pré estado, se existir.
   *
   * @return O pré estado deste estado.
   */
  public int getPreEstado() { return this.iPreEstado; }
  /**
   * Define um pré estado.
   *
   * @param preEstado Um novo pré estado para este estado.
   */
  public void setPreEstado(int iPreEstado) {
    this.iPreEstado = iPreEstado;
    this.personagem.getEstado(this.iPreEstado).setPosEstado(this.iID);
  }

  /**
   * Retorna referência para um pós estado, se existir.
   * 
   * @return O pós-estado deste estado.
   */
  public int getPosEstado() { return this.iPosEstado; }
  /**
   * Define um pós estado.
   * 
   * @param posEstado Um novo pós estado para este estado.
   */
  public GjME_Estado setPosEstado(int iPosEstado) {
    this.iPosEstado = iPosEstado;
    return this;
  }

  public abstract void executeNoEstado();
}