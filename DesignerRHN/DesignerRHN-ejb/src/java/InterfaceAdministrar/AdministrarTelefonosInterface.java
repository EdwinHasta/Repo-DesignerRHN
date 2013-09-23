/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposTelefonos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarTelefonosInterface {
    public List<Telefonos> telefonosPersona(BigInteger secPersona);
    public Personas encontrarPersona(BigInteger secPersona);
    public List<TiposTelefonos>  lovTiposTelefonos();
    public List<Ciudades>  lovCiudades();
    public void modificarTelefono(List<Telefonos> listaTelefonosModificar);
    public void borrarTelefono(Telefonos telefonos);
    public void crearTelefono(Telefonos telefonos);
}
