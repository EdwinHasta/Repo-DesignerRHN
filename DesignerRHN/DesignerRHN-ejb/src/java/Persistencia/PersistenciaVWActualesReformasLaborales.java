/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesReformasLaborales;
import InterfacePersistencia.PersistenciaVWActualesReformasLaboralesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesReformasLaborales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesReformasLaborales implements PersistenciaVWActualesReformasLaboralesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesReformasLaborales buscarReformaLaboral(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesReformasLaborales vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesReformasLaborales vWActualesReformasLaborales = (VWActualesReformasLaborales) query.getSingleResult();
            return vWActualesReformasLaborales;
        } catch (Exception e) {
            VWActualesReformasLaborales vWActualesReformasLaborales = null;
            return vWActualesReformasLaborales;
        }
    }
}
