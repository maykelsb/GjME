/*
 *
 * @author Maykel "Gardner" dos Santos braz <maykelsb@yahoo.com.br>
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
