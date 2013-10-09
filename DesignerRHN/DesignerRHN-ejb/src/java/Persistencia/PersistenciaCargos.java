package Persistencia;

import Entidades.Cargos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaCargosInterface;
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
            BigInteger in ;
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
}
