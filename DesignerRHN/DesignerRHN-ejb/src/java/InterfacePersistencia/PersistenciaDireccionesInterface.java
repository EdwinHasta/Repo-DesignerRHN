/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Direcciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaDireccionesInterface {
    public void crear(Direcciones telefonos);
    public void editar(Direcciones telefonos);
    public void borrar(Direcciones telefonos);
    public Direcciones buscarDireccion(BigInteger id);
    public List<Direcciones> buscarDirecciones();
    public List<Direcciones> direccionPersona(BigInteger secuenciaPersona);
    public List<Direcciones> direccionesPersona(BigInteger secuenciaPersona);
}
