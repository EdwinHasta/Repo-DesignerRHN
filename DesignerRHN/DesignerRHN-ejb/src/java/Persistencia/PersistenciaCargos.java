/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Cargos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Cargos cargos) {
        em.persist(cargos);
    }

    @Override
    public void editar(Cargos cargos) {
        em.merge(cargos);
    }


    @Override
    public void borrar(Cargos cargos) {
        em.remove(em.merge(cargos));
    }

    @Override
    public Cargos buscarCargoSecuencia(BigInteger secuencia) {
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
    public List<Cargos> consultarCargos() {
        try {
            Query query = em.createQuery("SELECT c FROM Cargos c ORDER BY c.nombre");
            List<Cargos> cargos = query.getResultList();
            return cargos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Cargos> cargosSalario() {
        try {
            List<Cargos> listaCargosSalario = consultarCargos();
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
    public List<Cargos> buscarCargosPorSecuenciaEmpresa(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM Cargos c  WHERE c.empresa.secuencia=:secEmpresa");
            query.setParameter("secEmpresa", secEmpresa);
            List<Cargos> cargos = query.getResultList();
            return cargos;
        } catch (Exception e) {
            System.out.println("Error buscarCargosPorSecuenciaEmpresa PersistenciaCargos : " + e.toString());
            return null;
        }
    }
}
