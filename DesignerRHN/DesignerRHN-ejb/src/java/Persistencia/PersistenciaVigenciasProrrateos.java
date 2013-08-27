package Persistencia;

import Entidades.VigenciasProrrateos;
import InterfacePersistencia.PersistenciaVigenciasProrrateosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasProrrateos implements PersistenciaVigenciasProrrateosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasProrrateos.
     */
    @Override
    public void crear(VigenciasProrrateos vigenciasProrrateos) {
        try {
            em.persist(vigenciasProrrateos);
        } catch (Exception e) {
            System.out.println("El registro VigenciasProrrateos no exite o esta reservada por lo cual no puede ser modificada (VigenciasProrrateos)");
        }
    }

    /*
     *Editar VigenciasProrrateos. 
     */
    @Override
    public void editar(VigenciasProrrateos vigenciasProrrateos) {
        try {
            em.merge(vigenciasProrrateos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasProrrateos");
        }
    }

    /*
     *Borrar VigenciasProrrateos.
     */
    @Override
    public void borrar(VigenciasProrrateos vigenciasProrrateos) {
        try {
            em.remove(em.merge(vigenciasProrrateos));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasProrrateos");
        }
    }

    /*
     *Encontrar un VigenciaProrrateo. 
     */
    @Override
    public VigenciasProrrateos buscarVigenciaProrrateo(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(VigenciasProrrateos.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaProrrateo PersistenciaVigenciasProrrateos");
            return null;
        }
    }

    /*
     *Encontrar todos los VigenciasProrrateos.
     */
    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateos() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasProrrateos.class));
        return em.createQuery(cq).getResultList();
        }catch(Exception e){
            System.out.println("Error buscarVigenciasProrrateos PersistenciaVigenciasProrrateos");
        return null;
        }
    }

    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateosEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vp FROM VigenciasProrrateos vp WHERE vp.viglocalizacion.empleado.secuencia = :secuenciaEmpl ORDER BY vp.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasProrrateos> vigenciasProrrateos = query.getResultList();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VigenciasProrrateos " + e);
            return null;
        }
    }

    @Override
    public VigenciasProrrateos buscarVigenciaProrrateoSecuencia(BigInteger secVP) {
        try {
            Query query = em.createNamedQuery("VigenciasProrrateos.findBySecuencia").setParameter("secuencia", secVP);
            VigenciasProrrateos vigenciasProrrateos = (VigenciasProrrateos) query.getSingleResult();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaProrrateoSecuencia PersistenciaVigenciasProrrateos");
            return null;
        }
    }

    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateosVigenciaSecuencia(BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT vp FROM VigenciasProrrateos vp WHERE vp.viglocalizacion.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            List<VigenciasProrrateos> vigenciasProrrateos = query.getResultList();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateosVigenciaSecuencia PersistenciaVigenciasProrrateos");
            return null;
        }
    }
}
