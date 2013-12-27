/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Nodos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaNodosInterface {

    public void crear(Nodos nodos);

    public void editar(Nodos nodos);

    public void borrar(Nodos nodos);

    public Nodos buscarNodoSecuencia(BigInteger secuencia);

    public List<Nodos> listNodos();

    public List<Nodos> buscarNodosPorSecuenciaHistoriaFormula(BigInteger secuencia);

}
