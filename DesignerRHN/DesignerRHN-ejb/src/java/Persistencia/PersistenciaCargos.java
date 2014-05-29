/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Cargos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import InterfacePersistencia.PersistenciaCargosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Cargos' de la base de
 * datos
 *
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaCargos implements PersistenciaCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Cargos cargos) {
        em.clear();
        em.getTransaction().begin();
        em.persist(cargos);
        em.getTransaction().commit();
    }

    @Override
    public void editar(EntityManager em, Cargos cargos) {
        em.clear();
        em.getTransaction().begin();
        em.merge(cargos);
        em.getTransaction().commit();
    }

    @Override
    public void borrar(EntityManager em, Cargos cargos) {
        em.clear();
        em.getTransaction().begin();
        em.remove(em.merge(cargos));
        em.getTransaction().commit();
    }

    @Override
    public Cargos buscarCargoSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            BigInteger in;
            in = (BigInteger) secuencia;
            return em.find(Cargos.class, in);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Cargos> consultarCargos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT c FROM Cargos c ORDER BY c.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Cargos> cargos = query.getResultList();
            return cargos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Cargos> cargosSalario(EntityManager em) {
        try {
            List<Cargos> listaCargosSalario = consultarCargos(em);
            if (listaCargosSalario != null) {
                for (int i = 0; i < listaCargosSalario.size(); i++) {
                    System.out.println("Secuencia: " + listaCargosSalario.get(i).getSecuencia());
                    String sqlQuery2 = "SELECT cargos_pkg.capturarsalario(?, sysdate) FROM DUAL";
                    Query query2 = em.createNativeQuery(sqlQuery2).setParameter(1, listaCargosSalario.get(i).getSecuencia());
                    listaCargosSalario.get(i).setSueldoCargo((BigDecimal) query2.getSingleResult());

                    System.out.println("Nombre Cargo: " + listaCargosSalario.get(i).getNombre());
                    System.out.println("Sueldo Cargo: " + listaCargosSalario.get(i).getSueldoCargo());
                }
            }
            return listaCargosSalario;
        } catch (Exception e) {
            System.out.println("PersistenciaCargos: Fallo el nativeQuery.cargosSalario " + e);
            return null;
        }
    }

    @Override
    public List<Cargos> buscarCargosPorSecuenciaEmpresa(EntityManager em, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM Cargos c  WHERE c.empresa.secuencia=:secEmpresa");
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Cargos> cargos = query.getResultList();
            return cargos;
        } catch (Exception e) {
            System.out.println("Error buscarCargosPorSecuenciaEmpresa PersistenciaCargos : " + e.toString());
            return null;
        }
    }
}
