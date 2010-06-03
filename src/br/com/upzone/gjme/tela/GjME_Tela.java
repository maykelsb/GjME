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
package br.com.upzone.gjme.tela;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;

import br.com.upzone.gjme.layer.GjME_TiledLayer;
import br.com.upzone.gjme.personagem.GjME_Personagem;

/**
 * Implementação de um GameCanvas para tratamento das telas do game.
 *
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 * @abstract
 */
public abstract class GjME_Tela extends GameCanvas implements Runnable {

  /**
   * Indica o status de execução atual da thread.
   */
  protected boolean bTelaAtiva = false;

  /**
   * Tempo de pausa entre atualizações da tela.
   */
  protected int iDelay = 100;

  /**
   * RGB da cor utilizada como fundo do game.
   */
  protected int iCorFundo = 0xFF00FF;

  /**
   * Gerenciador de camadas do game.
   */
  protected LayerManager lm = new LayerManager();

 /**
  * Construtor de telas.
  */
  public GjME_Tela() {
    super(true);
    this.setFullScreenMode(true);
  }

  /**
   * Inicia a thread de atualização da tela do game.
   */
  public void start() {
    this.bTelaAtiva = true;
    new Thread(this).start();
  }

  /**
   * Finaliza a thread de atualização da tela do game.
   */
  public void stop() { this.bTelaAtiva = false; }

  /**
   * Código de execução da thread faz leitura de inputs e desenha o conteúdo do layermanager.
   */
  public void run() {
    Graphics g = this.getGraphics();
    while (this.bTelaAtiva) {
      this.processarInput();
      this.atualizarLayers();
      this.desenhar(g);
      try {
        Thread.sleep(this.iDelay);
      } catch (InterruptedException iex) {
        System.out.println(iex.getMessage());
      }
    }
  }

  /**
   * Define a cor de fundo da tela do game e pinta o conteúdo do LayerManager.
   * @param g Recurso de desenho do sistema.
   */
  private void desenhar(Graphics g) {
    g.setColor(this.iCorFundo);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    this.lm.paint(g, 0, 0);
    this.flushGraphics();
  }

  /**
   * Atualiza as GjME_TiledLayer armazenadas no LayerManager.
   */
  private void atualizarLayers() {
    int iQtdLayers = this.lm.getSize();
    for (int i = 0; i < iQtdLayers; i++) {
      Layer lyr = this.lm.getLayerAt(i);
      if (lyr instanceof GjME_TiledLayer) {
        ((GjME_TiledLayer)lyr).atualizarPosicionamento();
      } else if (lyr instanceof GjME_Personagem) {
        ((GjME_Personagem)lyr).atualizarPersonagem();
      }
    }
  }

  /**
   * Processa os comandos inseridos pelo jogador.
   *
   * Dependendo da implementação da tela, podem ser comandos de movimentação
   * dos personagens, navegação em um menu, etc.
   */
  public abstract void processarInput();
}
