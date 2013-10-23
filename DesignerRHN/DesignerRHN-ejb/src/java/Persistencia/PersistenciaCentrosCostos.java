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
    
    @Override
    public List<CentrosCostos> buscarCentrosCostosEmpr(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM CentrosCostos cce WHERE cce.empresa.secuencia = :secuenciaEmpr AND cce.comodin='N' ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            List<CentrosCostos> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            return null;
        }
    }
    
        public long contadorSecuenciaEmpresa(BigInteger secEmpresa) {
        long so = 0;
        long vc = 0;
        long vp = 0;
        long total=0;
        try {
            Query query = em.createQuery("SELECT COUNT(so) FROM SolucionesNodos so WHERE so.secuencia = :secuenciaEmpr");
            query.setParameter("secuenciaEmpr", secEmpresa);
            so = query.getMaxResults();
            Query qury = em.createQuery("SELECT COUNT(vc) FROM VigenciasCuentas vc WHERE vc.secuencia = :secuenciaEmpr");
            qury.setParameter("secuenciaEmpr", secEmpresa);
            vc = qury.getMaxResults();
            Query que = em.createQuery("SELECT COUNT(vp) FROM VigenciasProrrateos vp WHERE vp.secuencia = :secuenciaEmpr");
            que.setParameter("secuenciaEmpr", secEmpresa);
            vp = que.getMaxResults();
            total=so+vc+vp;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            total = -1;
        } finally {
            return total;
        }
    }
}
