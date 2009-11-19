/*
 *
 * @author Maykel "Gardner" dos Santos braz <maykelsb@yahoo.com.br>
 */
package br.com.upzone.gjme.personagem;

import java.util.Hashtable;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import br.com.upzone.gjme.personagem.estado.Estado;

/**
 *
 * @abstract
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 */
public abstract class Personagem extends Sprite {

  /**
   * Indica o estado associado a ação de ficar parado;
   */
  public final static int PARADO = 0;

  /**
   * Armazena os estados válidos para personagens base.
   * 
   * Personagens jogadores/chefes de fase podem expandir esta a lista de
   * estados possíveis implementando novos estados.
   */
  protected Hashtable hstEstados = new Hashtable();

  /**
   * Indica qual o estado defaulto do persoangem.
   * 
   * Este estado é assumido quando o personagem não está executando nenhuma
   * ação. Geralmente será utilizado o estado pré-definido Personagem.PARADO.
   */
  protected Integer intEstadoDefault;

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
   * Adiciona estados à lista de estados válidos do personagem.
   * 
   * Cada um dos novos estados deve ser indentificado por uma constante
   * de identificação de estado como por exemplo: Personagem.PARADO;
   *
   * @param iID Valor de identificação do Estado. Ex: Personagem.PARADO;
   * @param std Novo estado do personagem;
   *
   * @see Personagem.PARADO;
   * @see Persoangem.hstEstados;
   * @see Estado;
   */
  protected void addEstado(int iID, Estado std) {
    this.hstEstados.put(new Integer(iID), std);
  }

  /**
   * Define o estado default do personagem.
   *
   * O estado padrão do personagem será utilizado sempre que o personagem não
   * executa nenhuma ação que modifique seu estado.
   *
   * @param iID Identificador do estado padrão do personagem;
   *
   * @see Personagem.intEstadoDefault;
   */
  protected void setEstadoDefault(int iID) {
    this.intEstadoDefault = new Integer(iID);
  }
}