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
package br.com.upzone.gjme;

/**
 *
 * @author Maykel "Gardner" dos Santos Braz <maykelsb@yahoo.com.br>
 * @final
 */
public final class GjME_Fisica {

  /**
   * Indica a aceleração da gravidade em pixels por frame.
   */
  public static final int GRAVIDADE = 2;

  /**
   * Indica a desaceleração sobre uma superficie coum.
   */
  public static final int ATRITO = 2;

  /**
   * Velocidade média de deslocamento dos personagens.
   */
  public static final int VELOCIDADE_MEDIA = 7;

  /**
   * Indica o lado direto.
   *
   * Deslocamento positivo no eixo X.
   */
  public final static int DIREITA = 0;
  /**
   * Indica o lado esquerdo.
   *
   * Deslocamento negativo no eixo X.
   */
  public final static int ESQUERDA = 1;

  /**
   * Indica o lado de baixo da tela.
   *
   * Deslocamento positivo no eixo Y.
   */
  public final static int BAIXO = 2;

  /**
   * Indica o lado de cima da tela.
   *
   * Deslocamento negativo no eixo Y.
   */
  public final static int CIMA = 3;
}