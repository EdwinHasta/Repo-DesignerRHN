package Persistencia;

import Entidades.VigenciasEstadosCiviles;
import InterfacePersistencia.PersistenciaVigenciasEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasEstadosCiviles implements PersistenciaVigenciasEstadosCivilesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Estado Civil.
     */
    @Override
    public void crear(VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        try {
            em.merge(vigenciasEstadosCiviles);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaVigenciasEstadosCiviles.crear: " + e);
        }
    }
    /*
     *Editar Vigencia Estado Civil. 
     */

    @Override
    public void editar(VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        em.merge(vigenciasEstadosCiviles);
    }

    /*
     *Borrar Vigencia Estado Civil.
     */
    @Override
    public void borrar(VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        em.remove(em.merge(vigenciasEstadosCiviles));
    }

    /*
     *Encontrar una Vigencia Estado Civil. 
     */
    @Override
    public VigenciasEstadosCiviles buscarVigenciaEstadoCivil(BigInteger id) {
        return em.find(VigenciasEstadosCiviles.class, id);
    }

    /*
     *Encontrar todas las Vigencias Estados Civiles.
     */
    @Override
    public List<VigenciasEstadosCiviles> buscarVigenciasEstadosCiviles() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasEstadosCiviles.class));
        return em.createQuery(cq).getResultList();
    }

    /*
     * Encontrar Estado Civil de una Persona.
     */
    @Override
    public List<VigenciasEstadosCiviles> vigenciaEstadoCivilPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vec) FROM VigenciasEstadosCiviles vec WHERE vec.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vec FROM VigenciasEstadosCiviles vec WHERE vec.persona.secuencia = :secuenciaPersona and vec.fechavigencia = (SELECT MAX(veci.fechavigencia) FROM VigenciasEstadosCiviles veci WHERE veci.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasEstadosCiviles> listaVigenciasEstadosCiviles = queryFinal.getResultList();
                return listaVigenciasEstadosCiviles;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEstadosCiviles.estadoCivilPersona" + e);
            return null;
        }
    }
}
