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
package br.com.upzone.gjme.tela;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

/*
 * @TODO Implementar DEBUG
 * @author Maykel "Gardner" dos Santos braz <maykelsb@yahoo.com.br>
 * @abstract
 */
public abstract class Tela extends GameCanvas implements Runnable {

  /**
   * Indica o status atual da tela.
   *
   * Quando ativa, indica que a thread desta tela está em execução. Quando
   * inativa, a thread da tela está parada ou deverá ser parada.
   */
  protected boolean bAtiva = false;

  /**
   * Tempo de espera do loop de atualização da tela.
   *
   * Este valor poderá ser alterado quando in-game para realizar efeitos de
   * camera lenta ou de fast forward.
   */
  protected int iDelay = 100;

  /**
   * Cor de fundo das camadas.
   */
  protected int iCorFundo = 0xFF00FF;

  /**
   * Gerênciador de layers da tela.
   */
  protected LayerManager layermanager = new LayerManager();

  /**
   * Controi uma nova tela.
   */
  public Tela() {
    super(true);
  }

  /**
   * Inicia a atualização da tela.
   */
  public void start() {
    this.bAtiva = true;
    new Thread(this).start();
  }

  /**
   * Interrompe a atualização da tela.
   */
  public void stop() {
    this.bAtiva = false;
  }

  public void run() {
    Graphics graphics = this.getGraphics();
    while (this.bAtiva) {
      this.processarInput();
      this.desenhar(graphics);
      try {
        Thread.sleep(this.iDelay);
      } catch (InterruptedException iex) {
        System.out.println(iex.getMessage());
      }
    }
  }

  /**
   * Desenha os objetos anexados ao layermanager da tela.
   *
   * Os objetos com representação gráfica que deverão ser pintados na tela
   * deverão estar anexados ao layermanager para que sejam pintados na tela.
   *
   * @param g Recurso de desenho.
   */
  public void desenhar(Graphics g) {
    g.setColor(this.iCorFundo);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    this.layermanager.paint(g, 0, 0);
    this.flushGraphics();
  }

  /**
   * Processa os comandos do jogador.
   *
   * A depender da implementação da tela, podem ser comandos de movimentação do
   * personagem jogador ou seleções em uma tela de menu.
   */
  public abstract void processarInput();
}
