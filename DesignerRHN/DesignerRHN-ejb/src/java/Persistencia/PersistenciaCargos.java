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
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class PersistenciaCargos implements PersistenciaCargosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear cargos.
     */
    @Override
    public void crear(Cargos cargos) {
        em.persist(cargos);
    }

    /*
     *Editar cargo. 
     */
    @Override
    public void editar(Cargos cargos) {
        em.merge(cargos);
    }

    /*
     *Borrar cargo.
     */
    @Override
    public void borrar(Cargos cargos) {
        em.remove(em.merge(cargos));
    }

    /*
     *Encontrar un cargo. 
     */
    @Override
    public Cargos buscarCargo(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(Cargos.class, in);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /*
     *Encontrar todos los cargos.
     */
    @Override
    public List<Cargos> buscarCargos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Cargos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Cargos> cargos() {
        try {
            Query query = em.createQuery("SELECT c FROM Cargos c ORDER BY c.nombre");
            List<Cargos> cargos = query.getResultList();
            return cargos;
        } catch (Exception e) {
            return null;
        }
    }

    /*@Override
     public List<CargoSalario> cargosSalario() {
     List<CargoSalario> listaCargosSalario;
     try {
     //String sqlQuery = "SELECT c.SECUENCIA, c.NOMBRE ,(select gs.salario from grupossalariales gs where gs.secuencia=c.GRUPOSALARIAL) salariocargo FROM CARGOS c ORDER BY c.NOMBRE";
     String sqlQuery = "SELECT c.NOMBRE FROM CARGOS c ORDER BY c.NOMBRE";
     //Query query = em.createNativeQuery(sqlQuery, CargoSalario.class);
     Query query = em.createNativeQuery(sqlQuery);
     //listaCargosSalario = query.getResultList();
     listaCargosSalario = null;
     List<String>
     return listaCargosSalario;
     } catch (Exception ex) {
     System.out.println("PersistenciaCargos: Fallo el nativeQuery.cargosSalario");
     return null;
     }
     }*/
    public List<Cargos> cargosSalario() {
        try {
            /*String sqlQuery = "SELECT c.* FROM CARGOS c ORDER BY c.NOMBRE";
            Query query = em.createNativeQuery(sqlQuery, Cargos.class);*/
            List<Cargos> listaCargosSalario = cargos();
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
}
