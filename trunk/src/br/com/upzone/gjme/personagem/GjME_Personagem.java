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
package br.com.upzone.gjme.personagem;

import java.util.Hashtable;
import java.util.Stack;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import br.com.upzone.gjme.GjME_Fisica;
import br.com.upzone.gjme.personagem.acao.GjME_Acao;

/**
 * Classe base para definição de personagens.
 *
 * Disponibiliza as seguintes características de um personagem:
 *   Pontos de vida;
 *   Direção para a qual o personagem está virado;
 *   Ações do personagem (Parar, Andar, etc.);
 * Disponibiliza os seguintes controladores para um personagem:
 *   Controle de transição de ações;
 *   Controladores de mudança de direção;
 * Faz sobrecarga dos seguintes métodos de Sprite:
 *   javax.microedition.lcdui.game.Sprite.nextFrame();
 *   javax.microedition.lcdui.game.Sprite.setFrameSequence();
 *
 * @TODO Definir pilha de execução de ações nesta classe para evitar que este
 * controle seja feito dentro dos objetos de estados.
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 * @abstract
 */
public abstract class GjME_Personagem extends Sprite {
  
  /**
   * Indica a ação que o personagem assume assim que é criado.
   */
  public final static int ACAO_INVALIDA = -1;
  /**
   * Indica a ação de ficar parado.
   */
  public final static int ACAO_AGUARDAR = 0;

  /**
   * Armazena as possíveis ações de um personagem.
   *
   * As ações são identificados pelos valores de constantes iniciadas em ACAO_ que
   * definem identificadores para cada uma das ações criadas para o personagem.
   */
  protected Hashtable hstAcoes = new Hashtable();

  /**
   * Indica que os frames do personagem devem ser refletidos ao mudar de direção.
   *
   * @see Personagem.setDirecaoPersonagem();
   */
  protected boolean bRefletirSprite = false;

  /**
   * Indica a direção para a qual o personagem está se movimentando
   *
   * Valores válidos são: GjME_Fisica.DIREITA e GjME_Fisicao.ESQUERDA.
   */
  protected int direcaoPersonagem = GjME_Fisica.DIREITA;

  /**
   * Quantidade de pontos de vida do personagem.
   */
  protected int iPontosDeVida = 0;

  /**
   * Velocidade de deslocamento do personagem no eixo X.
   */
  protected int iVelX = 0;
  /**
   * Velocidade de deslocamento do personagem no eixo Y.
   */
  protected int iVelY = 0;

  /**
   * Pilha de ações para execução.
   *
   * Sempre que uma nova ação deva ser executada, ela vai para o topo da pilha.
   * Se a ação no topo da pilha é finita, assim que ela for finalizada e removida
   * da pilha. O esquema de pré e pós ações será implementado com a ajuda desta
   * pilha, onde pré-ações são empilhadas após a ação atual e pós estados são
   * empilhados antes da ação atual.
   */
  protected Stack stkAcoes = new Stack();

  public void empilharAcao(int IDAcao) {
    if (((GjME_Acao)this.stkAcoes.peek()).retornaIDAcao() != IDAcao) {
      GjME_Acao acao = (GjME_Acao)this.hstAcoes.get(new Integer(IDAcao));
      //acao.defineEstadoExecucao(GjME_Acao.ACAO_EM_EXECUCAO);
      this.setFrameSequence(acao.retornaFrames());
      this.stkAcoes.push(acao);
    }
  }

  /**
   * Retorna a ação atual do personagem.
   * @return Ação que o personagem esta executando.
   */
  //public GjME_Acao retornaAcaoAtual() { return (GjME_Acao)this.stkAcoes.peek(); }

  /**
   * Retorna a ação identificada pelo ID informado.
   * @param ID Identificador da ação;
   * @return A ação identificado pelo ID.
   */
  //public GjME_Acao retornaAcao(int ID) {
//    return (GjME_Acao)this.hstAcoes.get(new Integer(ID));
//  }
  /**
   * Adiciona açoes à lista de ações válidas do personagem.
   *
   * Cada uma das novas ações é identificada por uma constante definda em
   * GjME_Personagem ou em suas descendentes. É recomendado que estas constantes
   * tem seu nome iniciado por ACAO_.
   *
   * @param iID Valor de identificação da ação. Ex: Personagem.ACAO_PARAR;
   * @param acao Nova ação do personagem;
   * @see GjME_Acao;
   */
  protected void adicionarAcao(int iID, GjME_Acao acao) {
    acao.defineIDAcao(iID);
    this.hstAcoes.put(new Integer(iID), acao);
  }

  /**
   * Retorna a direção para a qual o personagem está virado.
   *
   * @return Direção do personagem.
   * @see Personagem.direcaoPersonagem
   */
  public int direcaoPersonagem() { return this.direcaoPersonagem; }

  /**
   * Define uma nova direção para o personagem.
   *
   * @param iNovaDirecao Um dos valores representando DIREITA ou ESQUERDA;
   * @see Personagem.iDirecaoPersonagem;
   * @see javax.microedition.lcdui.game.Sprite.setTransform();
   */
  public void direcaoPersonagem(int iNovaDirecao) {
    if (this.bRefletirSprite && iNovaDirecao == GjME_Fisica.DIREITA) {
      this.setTransform(Sprite.TRANS_NONE);
    } else if (this.bRefletirSprite && iNovaDirecao == GjME_Fisica.ESQUERDA) {
      this.setTransform(Sprite.TRANS_MIRROR);
    }
    this.direcaoPersonagem = iNovaDirecao;
  }

  /**
   * Cria um novo personagem.
   *
   * Cria um personagem definindo seu pixel central como o pixel de referência e
   * posiciona o personagem no canvas com as coordenadas informadas.
   *
   * @param imgSS SpriteSheet com os frames de animação do personagem;
   * @param iWidth Largura dos frames em pixels;
   * @param iHeight Altura dos frames em pixels;
   * @param iX Coordenada X para posicionamento do personagem;
   * @param iY Coordenada Y para posicionamento do personagme;
   * @param iTelaLargura Largura da tela para cálculos de deslocamento do personagem;
   * @param iTelaAltura Altura da tela para cálculos de deslocamento do personagem;
   */
  public GjME_Personagem(Image imgSS, int iWidth, int iHeight,
          int iX, int iY, int iTelaLargura, int iTelaAltura) {
    super(imgSS, iWidth, iHeight);
    this.defineReferencePixel(iWidth / 2, iHeight / 2);
    this.setPosition(iX, iY);
  }

  /**
   * Troca de seqüência de frames do Sprite.
   *
   * Assim que é trocada a seqüência de frames, a flag Personagem.bSequenciaFramesTrocada
   * é setada como verdadeira. A flag é necessária para que a sobrecarga de
   * javax.microedition.lcdui.game.Sprite.nextFrame() funcione corretamente.
   *
   * @see Personagem.bSequenciaFramesTrocada;
   * @see Persoangem.nextFrame();
   * @see javax.microedition.lcdui.game.Sprite.setFrameSequence();
   */
  public void setFrameSequence(int[] iaFrames) {
    super.setFrameSequence(iaFrames);
    //this.bSequenciaFramesTrocada = true;
  }
  
  /**
   * Troca o frame atual do sprite pelo próximo na seqüência.
   *
   * 
   *
   *
   *
   *
   * Um personagem pode ter uma seqüência de frame que não seja repetida, neste
   * caso, é preciso controlar o que acontece assim que a seqüência chega ao fim.
   * Aqui, a seqüência de frames é alterada da maneira tradicional (chamando
   * Sprite.setFrameSequence()) para um único frame, o frame que caracteriza
   * o fim da animação do estado.
   * 
   * @see javax.microedition.lcdui.game.Sprite.nextFrame();
   * @see javax.microedition.lcdui.game.Sprite.setFrameSequence();
   * @see Personagem.bSequenciaFramesTrocada;
   * @see Estado.estadoContinuo();
   */
  public final void nextFrame() {
    GjME_Acao acao = (GjME_Acao)this.stkAcoes.peek();
    if (acao.acaoIniciada()) {
      if (!acao.animacaoContinua() && !acao.acaoFinalizada()
          && (this.getFrame() == acao.retornaFrames().length - 1)) {
        acao.defineEstadoExecucao(GjME_Acao.ACAO_FINALIZADA);
        return;
      } else if (acao.acaoFinalizada()) {
        acao = (GjME_Acao)this.stkAcoes.pop();
        acao.defineEstadoExecucao(GjME_Acao.ACAO_NAO_INICIADA);
        this.nextFrame();
      }
    } else {
      acao.defineEstadoExecucao(GjME_Acao.ACAO_EM_EXECUCAO);
      this.setFrame(0);
    }
    super.nextFrame();
  }

  public final void atualizarPersonagem() {
    this.nextFrame();
    if (GjME_Fisica.DIREITA == this.direcaoPersonagem) {
      if (this.iVelX > 0) { this.iVelX = Math.max(0, this.iVelX - GjME_Fisica.ATRITO); }
    } else if (GjME_Fisica.ESQUERDA == this.direcaoPersonagem) {
      if (this.iVelX < 0) { this.iVelX = Math.min(0, this.iVelX + GjME_Fisica.ATRITO); }
    }
    if (this.iVelY != 0) { this.iVelY = this.iVelY - GjME_Fisica.GRAVIDADE; }
    this.setPosition(this.getX() + this.iVelX, this.getY() + this.iVelY);
  }

  public final void desempilharAcoes() { }
}