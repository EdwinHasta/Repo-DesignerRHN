package Persistencia;

import Entidades.Tablas;
import InterfacePersistencia.PersistenciaTablasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author -Felipphe-
 */
@Stateless
public class PersistenciaTablas implements PersistenciaTablasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<Tablas> buscarTablas(BigInteger secuenciaMod) {

        try {
            /*Query query = em.createQuery("SELECT t FROM Tablas t WHERE t.modulo.secuencia = :secuenciaMod");*/
            Query query = em.createQuery("select t from Tablas t where t.modulo.secuencia = :secuenciaMod "
             +" and t.tipo in ('SISTEMA','CONFIGURACION' ) "
             +" and EXISTS (SELECT p FROM Pantallas p where t = p.tabla)"
             +"order by t.nombre");
            query.setParameter("secuenciaMod", secuenciaMod);
            List<Tablas> tablas = (List<Tablas>) query.getResultList();
            return tablas;

        } catch (Exception e) {
            System.out.println("Hola :$");
            List<Tablas> tablas = null;
            return tablas;
        }
    }
}
