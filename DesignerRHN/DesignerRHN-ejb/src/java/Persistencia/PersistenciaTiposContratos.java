package Persistencia;

import Entidades.TiposContratos;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaTiposContratos implements PersistenciaTiposContratosInterface{

        @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
        
        /*
     * Crear TiposContratos.
     */
    @Override
    public void crear(TiposContratos tiposContratos) {
        em.persist(tiposContratos);
    }

    /*
     *Editar TiposContratos. 
     */
    @Override
    public void editar(TiposContratos tiposContratos) {
        em.merge(tiposContratos);
    }

    /*
     *Borrar TiposContratos.
     */
    @Override
    public void borrar(TiposContratos tiposContratos) {
        em.remove(em.merge(tiposContratos));
    }

    /*
     *Encontrar un TiposContratos. 
     */

    @Override
    public TiposContratos buscarTipoContrato(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(TiposContratos.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todas los TiposContratos. 
     */
    @Override
    public List<TiposContratos> buscarTiposContratos() {

        //javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        /*CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         cq.select(cq.from(Empleados.class));
         return em.createQuery(cq).getResultList();
         */
        List<TiposContratos> tipoCLista = (List<TiposContratos>) em.createNamedQuery("TiposContratos.findAll").getResultList();
        return tipoCLista;
    }

    @Override
    public TiposContratos buscarTipoContratoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT e FROM TiposContratos e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposContratos tipoC = (TiposContratos) query.getSingleResult();
            return tipoC;
        } catch (Exception e) {
        }
        TiposContratos tipoC = null;
        return tipoC;
    }
    
     public List<TiposContratos> tiposContratos() {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposContratos tc ORDER BY tc.codigo");
            List<TiposContratos> tiposContratos = query.getResultList();
            return tiposContratos;
        } catch (Exception e) {
            return null;
        }
    }
}
