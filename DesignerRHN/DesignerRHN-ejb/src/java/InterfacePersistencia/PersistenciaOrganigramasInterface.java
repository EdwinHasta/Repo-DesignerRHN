/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Organigramas;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Organigramas' 
 * de la base de datos.
 * @author Hugo David Sin Gutiérrez.
 */
public interface PersistenciaOrganigramasInterface {
    /**
     * Método encargado de insertar un Organigrama en la base de datos.
     * @param organigramas Organigrama que se quiere crear.
     */
    public void crear(Organigramas organigramas);
    /**
     * Método encargado de modificar un Organigrama de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param organigramas Organigrama con los cambios que se van a realizar.
     */
    public void editar(Organigramas organigramas);
    /**
     * Método encargado de eliminar de la base de datos el Organigrama que entra por parámetro.
     * @param organigramas Organigrama que se quiere eliminar.
     */
    public void borrar(Organigramas organigramas);
    /**
     * Método encargado de buscar el Organigrama con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Organigrama que se quiere encontrar.
     * @return Retorna el Organigrama identificado con la secuencia dada por parámetro.
     */
    public Organigramas buscarOrganigrama(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Organigramas existentes en la base de datos.
     * @return Retorna una lista de Organigramas.
     */
    public List<Organigramas> buscarOrganigramas();
    /**
     * Método encargado de buscar los Organigramas de una Empresa, que estan vigentes a partir de una fecha específica.
     * @param secEmpresa Secuencia de la empresa a la que pertenecen los Organigramas.
     * @param fechaVigencia Fecha a partir de la cual se quieren los organigramas.
     * @return Retorna una lista de Organigramas.
     */
    public List<Organigramas> buscarOrganigramasVigentes(BigInteger secEmpresa, Date fechaVigencia);
    /**
     * Método encargado de buscar un Organigrama por su código. Este Organigrama se usara de base para crear un árbol.
     * @param codigoOrg Código del Organigrama que se quiere buscar.
     * @return Retorna un Organigrama.
     */
    public Organigramas organigramaBaseArbol(short codigoOrg);
}
