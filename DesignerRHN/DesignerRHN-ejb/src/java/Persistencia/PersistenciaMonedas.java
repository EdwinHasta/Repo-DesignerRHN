/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Monedas;
import InterfacePersistencia.PersistenciaMonedasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Monedas' de la base
 * de datos.
 *
 * @author Viktor
 */
@Stateless

public class PersistenciaMonedas implements PersistenciaMonedasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Monedas monedas) {
        em.persist(monedas);
    }

    @Override
    public void editar(EntityManager em, Monedas monedas) {
        em.merge(monedas);
    }

    @Override
    public void borrar(EntityManager em, Monedas monedas) {
        em.remove(em.merge(monedas));
    }

    @Override
    public BigInteger contadorProyectos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM proyectos WHERE tipomoneda = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println(" PersistenciaMonedas ContadorProyectos Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println(" PersistenciaMonedas contadorIdiomasPersonas Error : " + e);
            return retorno;
        }
    }

    @Override
    public Monedas consultarMoneda(EntityManager em, BigInteger secuenciaTI) {
        try {
            return em.find(Monedas.class, secuenciaTI);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Monedas> consultarMonedas(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM Monedas m ORDER BY m.codigo ASC ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Monedas> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }
}
