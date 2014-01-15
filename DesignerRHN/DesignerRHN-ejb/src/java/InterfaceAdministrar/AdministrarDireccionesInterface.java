/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Direcciones;
import Entidades.Personas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la
 * pantalla 'Direcciones'.
 *
 * @author betelgeuse
 */
public interface AdministrarDireccionesInterface {
    /**
     * Método encargado de crear Direcciones.
     * @param listaDirecciones Lista de las Cuentas que se van a crear.
     */
    public void crearDireccion(List<Direcciones> listaDirecciones);
    /**
     * Método encargado de editar Direcciones.
     * @param listaDirecciones Lista de las Cuentas que se van a modificar.
     */
    public void modificarDireccion(List<Direcciones> listaDirecciones);
    /**
     * Método encargado de borrar Direcciones.
     * @param listaDirecciones Lista de las Cuentas que se van a eliminar.
     */
    public void borrarDireccion(List<Direcciones> listaDirecciones);
    /**
     * Método encargado de recuperar las Direcciones según la Persona que tengan asociada.
     * @param secPersona Secuencia de la Persona por la cual se filtrara la búsqueda.
     * @return Retorna una lista de Direcciones.
     */
    public List<Direcciones> direccionesPersona(BigInteger secPersona);
    /**
     * Método encargado de recuperar una Persona dada su secuencia.
     * @param secPersona Secuencia de la Persona.
     * @return Retorna la Persona cuya secuencia coincida con el valor del parámetro. 
     */
    public Personas mostrarPersona(BigInteger secPersona);
    /**
     * Método encargado de recuperar las Ciudades necesarias para la lista de valores.
     * @return Retorna una lista de Ciudades.
     */
    public List<Ciudades> lovCiudades();
}
