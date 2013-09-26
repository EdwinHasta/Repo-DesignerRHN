/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasFormales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasFormalesInterface {

    public void crear(VigenciasFormales vigenciasFormales);

    public void editar(VigenciasFormales vigenciasFormales);

    public void borrar(VigenciasFormales vigenciasFormales);

    public List<VigenciasFormales> educacionPersona(BigInteger secuenciaPersona);
    
    public List<VigenciasFormales> buscarVigenciasFormales();
    
     public List<VigenciasFormales> vigenciasFormalesPersona(BigInteger secuenciaPersona);
}
