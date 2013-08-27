package Persistencia;

import Entidades.CentrosCostos;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaCentrosCostos implements PersistenciaCentrosCostosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear CentrosCostos.
     */

    @Override
    public void crear(CentrosCostos centrosCostos) {
        try{
        em.persist(centrosCostos);
        }catch(Exception e){
            System.out.println("Error crear PersistenciaCentrosCostos");
        }
    }

    /*
     *Editar CentrosCostos. 
     */

    @Override
    public void editar(CentrosCostos centrosCostos) {
        try{
        em.merge(centrosCostos);
        }catch(Exception e){
            System.out.println("Error editar PersistenciaCentrosCostos");
        }
    }

    /*
     *Borrar CentrosCostos.
     */

    @Override
    public void borrar(CentrosCostos centrosCostos) {
        try{
        em.remove(em.merge(centrosCostos));
        }catch(Exception e){
            System.out.println("Error borrar PersistenciaCentrosCostos");
        }
    }

    /*
     *Encontrar una CentroCosto.
     */

    @Override
    public CentrosCostos buscarCentroCosto(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(CentrosCostos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarCentroCosto PersistenciaCentrosCostos");
            return null;
        }

    }

    /*
     *Encontrar todas las CentrosCostos
     */

    @Override
    public List<CentrosCostos> buscarCentrosCostos() {
        try{
        List<CentrosCostos> centrosCostos = (List<CentrosCostos>) em.createNamedQuery("CentrosCostos.findAll").getResultList();
        return centrosCostos;
        }catch(Exception e){
            System.out.println("Error buscarCentrosCostos PersistenciaCentrosCostos");
            return null;
        }
    }


    @Override
    public CentrosCostos buscarCentroCostoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cc FROM CentrosCostos cc WHERE cc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            CentrosCostos centrosCostos = (CentrosCostos) query.getSingleResult();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentroCostoSecuencia PersistenciaCentrosCostos");
            CentrosCostos centrosCostos = null;
            return centrosCostos;
        }

    }
}
