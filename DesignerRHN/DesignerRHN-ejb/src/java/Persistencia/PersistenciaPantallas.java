/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Pantallas;
import InterfacePersistencia.PersistenciaPantallasInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Pantallas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPantallas implements PersistenciaPantallasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public Pantallas buscarPantalla(BigInteger secuenciaTab) {
        try {
            Query query = em.createQuery("SELECT p from Pantallas p where p.tabla.secuencia = :secuenciaTab");
            query.setParameter("secuenciaTab", secuenciaTab);
            Pantallas pantalla = (Pantallas) query.getSingleResult();
            return pantalla;

        } catch (Exception e) {
            Pantallas pantalla = null;
            return pantalla;
        }
    }
}
