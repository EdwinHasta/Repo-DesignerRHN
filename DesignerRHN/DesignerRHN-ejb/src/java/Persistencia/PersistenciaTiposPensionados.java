package Persistencia;

import Entidades.TiposPensionados;
import InterfacePersistencia.PersistenciaTiposPensionadosInterface;
import java.math.BigDecimal;
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
public class PersistenciaTiposPensionados implements PersistenciaTiposPensionadosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Tipo Pension.
     */

    @Override
    public void crear(TiposPensionados tiposPensionados) {
        try {
            em.persist(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposPensionados");
        }
    }

    /*
     *Editar Tipo Pension. 
     */

    @Override
    public void editar(TiposPensionados tiposPensionados) {
        try {
            em.merge(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposPensionados");
        }
    }

    /*
     *Borrar Tipo Pension.
     */

    @Override
    public void borrar(TiposPensionados tiposPensionados) {
        try {
            em.remove(em.merge(tiposPensionados));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposPensionados");
        }
    }

    /*
     *Encontrar una Tipo Pension. 
     */

    @Override
    public TiposPensionados buscarTipoPension(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TiposPensionados.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTipoPension PersistenciaTiposPensionados");
            return null;
        }

    }

    /*
     *Encontrar todas los Tipos Pensiones. 
     */

    @Override
    public List<TiposPensionados> buscarTiposPensionados() {
        try {
            List<TiposPensionados> tiposPensionadosLista = (List<TiposPensionados>) em.createNamedQuery("TiposPensionados.findAll").getResultList();
            return tiposPensionadosLista;
        } catch (Exception e) {
            System.out.println("Error buscarTiposPensionados PersistenciaTiposPensionados");
            return null;
        }
    }
    
    @Override
    public TiposPensionados buscarTipoPensionSecuencia(BigDecimal secuencia) {

        try {
            Query query = em.createQuery("SELECT tp FROM TiposPensionados tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposPensionados tipoP = (TiposPensionados) query.getSingleResult();
            return tipoP;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPensionSecuencia PersistenciaTiposPensionados");
            TiposPensionados tipoP = null;
            return tipoP;
        }

    }

}
