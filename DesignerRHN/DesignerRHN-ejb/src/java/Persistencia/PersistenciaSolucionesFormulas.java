/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSolucionesFormulasInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'SolucionesFormulas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaSolucionesFormulas implements PersistenciaSolucionesFormulasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public int validarNovedadesNoLiquidadas(BigInteger secNovedad) {
        try {
            Query query = em.createQuery("SELECT COUNT(sf) FROM SolucionesFormulas sf WHERE sf.novedad.secuencia = :secNovedad");
            query.setParameter("secNovedad", secNovedad);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Exepcion: (validarNovedadesNoLiquidadas) " + e);
            return 1;
        }
    }
}
