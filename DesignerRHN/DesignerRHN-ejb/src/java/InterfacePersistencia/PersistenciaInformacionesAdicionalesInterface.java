/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.InformacionesAdicionales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaInformacionesAdicionalesInterface {
    public void crear(InformacionesAdicionales informacionesAdicionales);
    public void editar(InformacionesAdicionales informacionesAdicionales);
    public void borrar(InformacionesAdicionales informacionesAdicionales);
    public InformacionesAdicionales buscarinformacionAdicional(BigInteger id);
    public List<InformacionesAdicionales> buscarinformacionesAdicionales();
    public List<InformacionesAdicionales> informacionAdicionalPersona(BigInteger secuenciaEmpleado);
}
