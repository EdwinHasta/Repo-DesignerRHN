/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.ClasesAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'ClasesAccidentes'. 
 * @author betelgeuse
 */
@Local
public interface AdministrarClasesAccidentesInterface {
    /**
     * Método encargado de crear ClasesAccidentes.
     * @param listaClasesAccidentes Lista de los ClasesAccidentes que se van a crear.
     */
    public void crearClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes);
    /**
     * Método encargado de editar ClasesAccidentes.
     * @param listaClasesAccidentes Lista de los ClasesAccidentes que se van a modificar.
     */
    public void modificarClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes);
    /**
     * Método encargado de borrar ClasesAccidentes.
     * @param listaClasesAccidentes Lista de los ClasesAccidentes que se van a eliminar.
     */
    public void borrarClasesAccidentes(List<ClasesAccidentes> listaClasesAccidentes);
    /**
     * Método encargado de recuperar todos los ClasesAccidentes.
     * @return Retorna una lista de ClasesAccidentes.
     */
    public List<ClasesAccidentes> listaClasesAccidentes();
    /**
     * Método encargado de recuperar una ClaseAccidente dada su secuencia.
     * @param secClasesAccidentes Secuencia de la ClaseAccidente.
     * @return Retorna la ClaseAccidente cuya secuencia coincida con el valor del parámetro. 
     */
    public ClasesAccidentes mostrarClaseAccidente(BigInteger secClasesAccidentes);
    
    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos);
}
