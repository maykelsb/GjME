/*
 *
 * @author Maykel "Gardner" dos Santos braz <maykelsb@yahoo.com.br>
 */
package br.com.upzone.gjme.personagem.estado;

import br.com.upzone.gjme.personagem.Personagem;

/**
 * @TODO OS STATUS DOS PRÉ ESTADOS DEVEM SER REDEFINIDOS LOGO QUE O ESTADO PRINCIPAL
 * @TODO É REINICIADO / INICIADO
 * @abstract
 */
public abstract class Estado {

  /**
   * Indica que o estado ainda não iniciou seu ciclo de execução.
   */
  protected static final int PARADO = -1;
  /**
   * Este status indica que o estado está em execução.
   */
  protected static final int EM_EXECUCAO = 0;
  /**
   * Indica que o estado já chegou ao fim de sua execução.
   */
  protected static final int FINALIZADO = 1;

  /**
   * Indica o status atual do Estado.
   * @see Estado.PARADO;
   * @see Estado.EM_EXECUCAO;
   * @see Estado.FINALIZADO;
   */
  private int iStatus = Estado.PARADO;

  /**
   * Referência para o personagem a quem o estado pertence.
   *
   * @see br.com.upzone.gjme.personagem.Personagem
   */
  protected Personagem personagem;

  /**
   * Indica um estado que deve ser executado antes deste.
   */
  protected Estado preEstado = null;
  /**
   * Indica um estado que deve ser executado após este.
   */
  protected Estado posEstado = null;

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
  public Estado(Personagem psg, int[] iarFrames) {
    this.personagem = psg;
    this.iarFrames = iarFrames;
  }

  /**
   * Constroi um novo estado para um personagem com uma seqüência regular de frames.
   * 
   * @param psg Referência para o personagem dono do estado;
   * @param iFrameInicial O identificador do frame inicial da animação;
   * @param iFrameFinal O identificador do frame final da animação;
   */
  public Estado(Personagem psg, int iFrameInicial, int iFrameFinal) {
    this.personagem = psg;
    this.iarFrames = new int[iFrameFinal - iFrameInicial + 1];
    int j = 0;
    for (int i = iFrameInicial; i <= iFrameFinal; i++) {
      this.iarFrames[j++] = i;
    }
  }

  /**
   * Retorna referência para um pré estado, se existir.
   *
   * @return O pré estado deste estado.
   */
  public Estado getPreEstado() { return this.preEstado; }
  /**
   * Define um pré estado.
   *
   * @param preEstado Um novo pré estado para este estado.
   */
  public void setPreEstado(Estado preEstado) {
    this.preEstado = preEstado;
    this.preEstado.setPosEstado(this);
  }

  /**
   * Retorna referência para um pós estado, se existir.
   * 
   * @return O pós-estado deste estado.
   */
  public Estado getPosEstado() { return this.posEstado; }
  /**
   * Define um pós estado.
   * 
   * @param posEstado Um novo pós estado para este estado.
   */
  public void setPosEstado(Estado posEstado) {
    this.posEstado = posEstado;
    this.posEstado.setPreEstado(this);
  }

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

  // @TODO pode avaliar a execução de um pré estado
  //protected abstract boolean ValidaEstado();
  
  // @TODO pode setar um preh estado para ser executado
  protected abstract void IniciaEstado();

  // @TODO Pode ser utiliziado para lançamento de projéteis;
  protected abstract void FinalizaEstado();
}