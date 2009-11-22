/*
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

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import br.com.upzone.gjme.Direcoes;
import br.com.upzone.gjme.personagem.estado.Estado;

/**
 * Classe base para definição de personagens.
 *
 * Disponibiliza as seguintes características de um personagem:
 *   * Pontos de vida;
 *   * Direção para a qual o personagem está virado;
 *   * Velocidade de deslocamento;
 *   * Estado do personagem (Parado, Andando, etc.);
 * Disponibiliza os seguintes controladores para um personagem:
 *   * Controle de mundaça de estados;
 *   * Controladores de mudança de direção;
 * Faz sobrecarga dos seguintes métodos de Sprite:
 *   * javax.microedition.lcdui.game.Sprite.nextFrame();
 *   * javax.microedition.lcdui.game.Sprite.setFrameSequence();
 *
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 * @abstract
 */
public abstract class Personagem extends Sprite {
  
  /**
   * Indica o estado inicial do personagem, que é um estado inválido.
   */
  public final static int EST_PERSON_INVALIDO = -1;
  /**
   * Indica o estado associado a ação de ficar parado;
   */
  public final static int EST_PERSON_PARADO = 0;

  /**
   * Identificador do estado atual do personagem.
   */
  protected int iEstadoAtual = Personagem.EST_PERSON_INVALIDO;
  /**
   * Faz a alteração de estado atual do personagem.
   *
   * Ao realizar a troca de estado, seta o atual estado como o último estado e
   * define a seqüência de frames do estado como a seqüência em execução. Sempre
   * que um novo estado é setado, sua seqüência de frames é definida como a
   * seqüência de frames do personagem e seu status é setado como STS_EST_EM_EXECUCAO.
   * A troca de estados do personagem só acontece se o estado em que o personagem
   * se encontra for diferente do novo estado.
   *
   * @param iID Identificador do novo estado do personagem;
   *
   * @see javax.microedition.lcdui.game.Sprite;
   * @see Estado.STS_EST_EM_EXECUCAO;
   */
  public void setEstado(int iID) {
    if (this.iEstadoAtual != iID) {
      this.iEstadoAtual = iID;
      this.estadoAtual = (Estado)this.hstEstados.get(
        new Integer(this.iEstadoAtual));
      this.estadoAtual.setStatus(Estado.STS_EST_EM_EXECUCAO);
      this.setFrameSequence(this.estadoAtual.getFrames());
    }
  }

  /**
   * Referência para o estado atual do personagem.
   *
   * Esta referência é utilizada para facilitar o controle de frames e de
   * mudança de estados e seqüência de frames do personagem.
   *
   * @see Personagem.nextFrame();
   */
  protected Estado estadoAtual = null;
  /**
   * Retorna o estado atual do personagem.
   *
   * @return Estado atual do pesonagem. Personagem.estadoAtual;
   */
  public Estado getEstado() { return this.estadoAtual; }

  /**
   * Indica que a seqüência de frames do personagem acabou de ser trocada.
   *
   * Este atributo evita que o primeiro frame da seqüência atual seja pulada
   * com a sobrecarga de javax.microedition.lcdui.game.Sprite.nextFrame() ao
   * trocar a seqüência, o primeiro frame era ignorado, avançando já para o segundo.
   *
   * @see Personagem.setFrameSequence();
   * @see Personagem.nextFrame();
   */
  private boolean bSequenciaFramesTrocada = false;

  /**
   * Armazena os estados válidos para personagens base.
   * 
   * Personagens jogadores/chefes de fase podem expandir esta a lista de
   * estados possíveis implementando novos estados.
   */
  protected Hashtable hstEstados = new Hashtable();
  /**
   * Retorna o estado identificado pelo ID informado.
   *
   * @param iID O Identificador do estado desejado.
   */
  public Estado getEstado(int iID) {
    return (Estado)this.hstEstados.get(new Integer(this.iEstadoAtual));
  }
  /**
   * Adiciona estados à lista de estados válidos do personagem.
   *
   * Cada um dos novos estados deve ser indentificado por uma constante
   * de identificação de estado como por exemplo: Personagem.PARADO;
   *
   * @param iID Valor de identificação do Estado. Ex: Personagem.EST_PERSON_PARADO;
   * @param std Novo estado do personagem;
   * @see Personagem.EST_PERSON_PARADO;
   * @see Persoangem.hstEstados;
   * @see Estado;
   */
  protected void addEstado(int iID, Estado std) {
    this.hstEstados.put(new Integer(iID), std);
  }

  /**
   * Indica a direção para a qual o personagem está virado.
   *
   * @see Direcoes;
   */
  private int iDirecaoPersonagem = Direcoes.DIREITA;
  /**
   * Retorna a direção para a qual o personagem está virado.
   *
   * @return Direção do personagem.
   * @see Personagem.iDirecaoPersonagem
   */
  public int getDirecaoPersonagem() { return this.iDirecaoPersonagem; }

  /**
   * Define uma nova direção para o personagem.
   *
   * @param iNovaDirecao Um dos valores representando DIREITA ou ESQUERDA;
   * @see Personagem.iDirecaoPersonagem
   */
  public void setDirecaoPersonagem(int iNovaDirecao) {
    this.iDirecaoPersonagem = iNovaDirecao;
  }

  protected int iTelaLargura;
  protected int iTelaAltura;





  /**
   * Quantidade de pontos de vida do personagem.
   */
  protected int iPontosDeVida = 0;
  /**
   * Velocidade de deslocamento do personagem andando.
   * Se o personagem estiver correndo, aumente em 50%;
   */
  protected int iVelDeslocamento = 5;

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
   */
  public Personagem(Image imgSS, int iWidth, int iHeight, int iX, int iY) {
    super(imgSS, iWidth, iHeight);
    this.defineReferencePixel(iWidth / 2, iHeight / 2);
    this.setPosition(iX, iY);
  }

  /**
   * Cria um novo personagem com determinada velocidade de deslocamento.
   * 
   * @param iVelDeslocamento A velocidade de deslocamento do personagem.
   * 
   * @see Personagem
   */
  public Personagem(Image imgSS, int iWidth, int iHeight, int iX, int iY,
          int iVelDeslocamento) {
    this(imgSS, iWidth, iHeight, iX, iY);
    this.iVelDeslocamento = iVelDeslocamento;
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
    this.bSequenciaFramesTrocada = true;
  }
  
  /**
   * Troca o frame atual do sprite pelo próximo na seqüência.
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
  public void nextFrame() {
    if (this.bSequenciaFramesTrocada) {
      this.bSequenciaFramesTrocada = false;
      this.setFrame(0);
    } else {
      if (!this.estadoAtual.estadoContinuo()) {
        if (!this.estadoAtual.finalizado()) {
          if (this.getFrame() == this.estadoAtual.getFrames().length - 1) {
            this.estadoAtual.setStatus(Estado.STS_EST_FINALIZADO);
            super.setFrameSequence(new int[] {
              this.estadoAtual.getFrames()[this.estadoAtual.getFrames().length - 1]});
            if (this.estadoAtual.posEstadoValido()) {
              this.setEstado(this.estadoAtual.getPosEstado());
            }
          }
        }
      }
      super.nextFrame();
    }
  }




  public abstract void setTamanhoTela(int iLargura, int iAltura);
}