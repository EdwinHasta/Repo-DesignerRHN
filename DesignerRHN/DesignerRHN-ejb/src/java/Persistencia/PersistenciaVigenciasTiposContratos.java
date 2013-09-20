package Persistencia;

import Entidades.VigenciasTiposContratos;
import InterfacePersistencia.PersistenciaVigenciasTiposContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class PersistenciaVigenciasTiposContratos implements PersistenciaVigenciasTiposContratosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear vigenciasCargos.
     */
    @Override
    public void crear(VigenciasTiposContratos vigenciaTipoContrato) {
        try {
            em.merge(vigenciaTipoContrato);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasTiposContratos)" + e);
        }
    }

    /*
     *Editar vigenciasCargos. 
     */
    @Override
    public void editar(VigenciasTiposContratos vigenciaTipoContrato) {
        try {
            em.merge(vigenciaTipoContrato);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Tipo Contrato");
        }
    }

    /*
     *Borrar vigenciasCargos.
     */
    @Override
    public void borrar(VigenciasTiposContratos vigenciaTipoContrato) {
        em.remove(em.merge(vigenciaTipoContrato));
    }

    /*
     *Encontrar una vigenciasCargos. 
     */
    @Override
    public VigenciasTiposContratos buscarVigenciaTipoContrato(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(VigenciasTiposContratos.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las vigenciasCargos.
     */
    @Override
    public List<VigenciasTiposContratos> buscarVigenciasTiposContratos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasTiposContratos.class));
        return em.createQuery(cq).getResultList();
    }


    @Override
    public List<VigenciasTiposContratos> buscarVigenciaTipoContratoEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vtc FROM VigenciasTiposContratos vtc WHERE vtc.empleado.secuencia = :secuenciaEmpl ORDER BY vtc.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            
            List<VigenciasTiposContratos> vigenciasTiposContratos = (List<VigenciasTiposContratos>) query.getResultList();
           // System.out.println("Ciudad:  " + vigenciasTiposContratos.get(6).getCiudad());
            return vigenciasTiposContratos;
        } catch (Exception e) {
            System.out.println("Error llave: " + e);
            return null;
        }

    }
}
