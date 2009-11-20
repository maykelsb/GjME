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
   * ID de identificação do estado.
   * 
   * Este ID é utilizado para setar seqüências de estados, defindo este como
   * um pos estado, por exemplo.
   */
  private int iID;

  /**
   * Referência para o personagem a quem o estado pertence.
   *
   * @see br.com.upzone.gjme.personagem.Personagem
   */
  protected Personagem personagem;

  /**
   * Indica um estado que deve ser executado antes deste.
   */
  protected int iPreEstado;
  /**
   * Indica um estado que deve ser executado após este.
   */
  protected int iPosEstado;

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
  public Estado(Personagem psg, int iID, int[] iarFrames) {
    this.personagem = psg;
    this.iarFrames = iarFrames;
    this.iID = iID;
  }

  /**
   * Constroi um novo estado para um personagem com uma seqüência regular de frames.
   * 
   * @param psg Referência para o personagem dono do estado;
   * @param iFrameInicial O identificador do frame inicial da animação;
   * @param iFrameFinal O identificador do frame final da animação;
   */
  public Estado(Personagem psg, int iID, int iFrameInicial, int iFrameFinal) {
    this.iarFrames = new int[iFrameFinal - iFrameInicial + 1];
    int j = 0;
    for (int i = iFrameInicial; i <= iFrameFinal; i++) {
      this.iarFrames[j++] = i;
    }
    this.iID = iID;
  }

  public int[] getFrames() {
    return this.iarFrames;
  }

  /**
   * Retorna referência para um pré estado, se existir.
   *
   * @return O pré estado deste estado.
   */
  public int getPreEstado() { return this.iPreEstado; }
  /**
   * Define um pré estado.
   *
   * @param preEstado Um novo pré estado para este estado.
   */
  public void setPreEstado(int iPreEstado) {
    this.iPreEstado = iPreEstado;
    this.personagem.getEstado(this.iPreEstado).setPosEstado(this.iID);
  }

  /**
   * Retorna referência para um pós estado, se existir.
   * 
   * @return O pós-estado deste estado.
   */
  public int getPosEstado() { return this.iPosEstado; }
  /**
   * Define um pós estado.
   * 
   * @param posEstado Um novo pós estado para este estado.
   */
  public void setPosEstado(int iPosEstado) {
    this.iPosEstado = iPosEstado;
    this.personagem.getEstado(this.iPosEstado).setPreEstado(this.iID);
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
  public abstract void iniciaEstado();

  // @TODO Pode ser utiliziado para lançamento de projéteis;
  protected abstract void finalizaEstado();

  public abstract void processarInput(int iKeyState);
}