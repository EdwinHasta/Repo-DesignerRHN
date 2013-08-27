/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Telefonos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaTelefonosInterface {
    public void crear(Telefonos telefonos);
    public void editar(Telefonos telefonos);
    public void borrar(Telefonos telefonos);
    public Telefonos buscarTelefono(BigInteger id);
    public List<Telefonos> buscarTelefonos();
    public List<Telefonos> telefonoPersona(BigInteger secuenciaPersona);
}
