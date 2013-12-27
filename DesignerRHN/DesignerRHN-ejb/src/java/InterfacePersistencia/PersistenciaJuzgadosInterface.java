/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.Juzgados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaJuzgadosInterface {
    public void crear(Juzgados juzgados);
    public void editar(Juzgados juzgados) ;
    public void borrar(Juzgados juzgados) ;
    public Juzgados buscarJuzgado(BigInteger secuenciaJ);
    public List<Juzgados> buscarJuzgados() ;
   public List<Juzgados> buscarJuzgadosPorCiudad(BigInteger secCiudad) ;
    public BigInteger contadorEerPrestamos(BigInteger secuencia) ;
}
