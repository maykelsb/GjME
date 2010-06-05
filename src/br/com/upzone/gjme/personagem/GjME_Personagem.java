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

  /**
   * Empilha as ações que o personagem deve realizar em ordem de execução.
   *
   * Também faz o controle do empilhamento das pré e pós ações respeitando a
   * sua prioridade. Reseta o estado da ação que ficou empilhada (a antiga ação
   * atual) para que possa ser executada posteriormente sem problemas.
   * @param IDAcao A ação que deve ser executada.
   *
   * @TODO Pode ser que algumas ações ao serem empilhadas, cancelem outras previamente
   * empilhadas, ou podem exigir que açoes anteriores sejam continuadas do ponto em
   * que foram interrompidas. Pensar em como resolver estes impasses e se eles são
   * mesmo necessários ou existem.
   */
  public void empilharAcao(int IDAcao) { System.out.println(IDAcao);
    boolean bEmpilhar = false;
    if (this.stkAcoes.empty()) { bEmpilhar = true;
    } else {
      GjME_Acao acaoAtual = (GjME_Acao)this.stkAcoes.peek();
      if (acaoAtual.retornaIDAcao() != IDAcao) {
        bEmpilhar = true;
        // -- Reinicia a ação anterior para que ela possa ser iniciada posteriormente sem problemas
        acaoAtual.defineEstadoExecucao(GjME_Acao.ACAO_NAO_INICIADA);
      } else if (acaoAtual.acaoFinalizada()) {
        acaoAtual.defineEstadoExecucao(GjME_Acao.ACAO_EM_EXECUCAO);
      }
    }

    if (bEmpilhar) {
      // -- Adicionando as novas ações
      GjME_Acao acao = (GjME_Acao)this.hstAcoes.get(new Integer(IDAcao));
      // -- Empilhando pós ação
      if (acao.temPosAcao()) { this.empilharAcao(acao.retornaIDPosAcao()); }
      // -- Empilhando esta ação
      this.stkAcoes.push(acao);
      // -- Empilhando pré ação
      if (acao.temPreAcao()) { this.empilharAcao(acao.retornaIDPreAcao()); }
    }
  }

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
   * Troca o frame atual do sprite pelo próximo na seqüência e, se necessário, desempilha ações.
   *
   * Quando uma seqüência de frames de uma ação não é do tipo contínuo, ela deve
   * exibir apenas o último frame até que a ação seja trocada. Assim que uma ação
   * é finalizada, ela é removida e a ação anterior continua a ser executada.
   * 
   * @see javax.microedition.lcdui.game.Sprite.nextFrame();
   * @see javax.microedition.lcdui.game.Sprite.setFrameSequence();
   */
  public final void nextFrame() {
    GjME_Acao acao = (GjME_Acao)this.stkAcoes.peek();
    if (acao.acaoIniciada()) { // -- Manutenção de ações em execução
      // -- Se a ação não for do tipo contínua, não está finalizada e atingiu o
      // -- o último frame da animação, o estado é finalizado e o frame não é
      // -- atualizado.
      if (!acao.animacaoContinua() && !acao.acaoFinalizada()
          && (this.getFrame() == acao.retornaFrames().length - 1)) {
        acao.defineEstadoExecucao(GjME_Acao.ACAO_FINALIZADA);
        return;
      } else if (acao.acaoFinalizada()) {
        // -- Se a ação foi finalizada, remova-a da pilha e atualize seu estado
        // -- para não iniciada permitindo que ela seja reutilizada posteriormente
        // -- sem problemas.
        acao = (GjME_Acao)this.stkAcoes.pop();
        acao.defineEstadoExecucao(GjME_Acao.ACAO_NAO_INICIADA);
        this.nextFrame();
      }
    } else {
      // -- Iniciando a execução de uma ação
      acao.defineEstadoExecucao(GjME_Acao.ACAO_EM_EXECUCAO);
      this.setFrameSequence(acao.retornaFrames());
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

  /**
   * Desempilha as ações da pilha de ações até chegar a GjME_Personagem.ACAO_AGUARDAR.
   */
  public final void desempilharAcoes() {
    while (GjME_Personagem.ACAO_AGUARDAR != ((GjME_Acao)this.stkAcoes.peek()).retornaIDAcao()) {
      GjME_Acao acao = (GjME_Acao)this.stkAcoes.pop();
      acao.defineEstadoExecucao(GjME_Acao.ACAO_NAO_INICIADA);
    }
  }
}