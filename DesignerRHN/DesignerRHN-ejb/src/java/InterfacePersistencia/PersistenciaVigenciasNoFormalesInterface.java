/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasNoFormales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasNoFormalesInterface {
    
    public void crear(VigenciasNoFormales vigenciasNoFormales);
    public void editar(VigenciasNoFormales vigenciasNoFormales);
    public void borrar(VigenciasNoFormales vigenciasNoFormales);
    public List<VigenciasNoFormales> buscarVigenciasNoFormales();
    public List<VigenciasNoFormales> vigenciasNoFormalesPersona(BigInteger secuenciaPersona);
    
}
