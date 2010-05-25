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
package br.com.upzone.gjme.layer;

import javax.microedition.lcdui.Image;

import javax.microedition.lcdui.game.TiledLayer;

/**
 * Implementação de uma tiledlayer para o game, com funções de auxílio.
 *
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 */
public final class GjME_TiledLayer extends TiledLayer {

  /**
   * Tiled layer que não apresenta rolagem.
   */
  public static final int TL_FIXA = 1;
  /**
   * Tiled layer com rolagem contínua no eixo X.
   */
  public static final int TL_ROLAGEM_X = 2;
  /**
   * Tiled layer com rolagem contínua no eixo Y.
   */
  public static final int TL_ROLAGEM_Y = 3;
  /**
   * Tiled layer com rolagem fixa no personagem, acompanhando seu deslocamento.
   */
  public static final int TL_SEGUIDORA = 4;

  /**
   * Indica o comportamento atual da tiled layer.
   *
   * O comportamento inicial da tiled layer é o GjME_TiledLayer.TL_FIXA.
   * @see GjME_TiledLayer.TL_FIXA
   * @see GjME_TiledLayer.TL_ROLAGEM_X
   * @see GjME_TiledLayer.TL_ROLAGEM_Y
   * @see GjME_TiledLayer.TL_SEGUIDORA
   */
  private int iComportamento = GjME_TiledLayer.TL_FIXA;

  /**
   * 
   * @param imgTileset Imagem com o tileset para criação da tiled layer;
   * @param iTileWidth Largura dos tiles da tiled layer;
   * @param iTileHeight Altura dos tiles da tiled layer;
   * @return Referência para tiled layer já adicionada ao layer managem.
   */
  public GjME_TiledLayer (int[][] iarTiles, Image imgTileset, int iLarguratile, int iAlturaTile) {
    super(iarTiles[0].length, iarTiles.length, imgTileset, iLarguratile, iAlturaTile);
    this.posicionarTiles(iarTiles);
  }

  /**
   * Define o comportamento da tiled layer.
   * 
   * Se o comportamento informado não for válido, é atribuído o comportamento
   * GjME_TiledLayer.TL_FIXA.
   */
  public void setComportamento(int iComp) {
    if ((iComp >= GjME_TiledLayer.TL_FIXA) && (iComp <= GjME_TiledLayer.TL_SEGUIDORA)) {
      this.iComportamento = iComp;
    } else { this.iComportamento = GjME_TiledLayer.TL_FIXA; }
  }

  /**
   * Posiciona os tiles para montar a tiled layer.
   * @param iarTiles Matrix bidimensional com as configurações da tiled layer;
   */
  private void posicionarTiles(int iarTiles[][]) {
    int iColunas = iarTiles[0].length,
        iLinhas = iarTiles.length;
    // -- Preenchendo a Tiled layer com os tiles da fase de acordo com iarTiles
    for (int iCol = 0; iCol < iColunas; iCol++) {
      for (int iLin = 0; iLin < iLinhas; iLin++) {
        this.setCell(iCol, iLin, iarTiles[iLin][iCol]);
      }
    }
  }

  public void atualizarPosicionamento() {
   switch (this.iComportamento) {
     case GjME_TiledLayer.TL_FIXA: break;
     case GjME_TiledLayer.TL_ROLAGEM_X: break;
     case GjME_TiledLayer.TL_ROLAGEM_Y: break;
     case GjME_TiledLayer.TL_SEGUIDORA: break;
     default: break;
   }
  }
}
