/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSoCondicionesTrabajosInterface;
import Entidades.SoCondicionesTrabajos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'SoCondicionesTrabajos'
 * de la base de datos.
 * @author John Pineda.
 */
@Stateful
public class PersistenciaSoCondicionesTrabajos implements PersistenciaSoCondicionesTrabajosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, SoCondicionesTrabajos soCondicionesTrabajos) {
        em.persist(soCondicionesTrabajos);
    }

    @Override
    public void editar(EntityManager em, SoCondicionesTrabajos soCondicionesTrabajos) {
        em.merge(soCondicionesTrabajos);
    }

    @Override
    public void borrar(EntityManager em, SoCondicionesTrabajos soCondicionesTrabajos) {
        em.remove(em.merge(soCondicionesTrabajos));
    }

    @Override
    public SoCondicionesTrabajos buscarSoCondicionTrabajo(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(SoCondicionesTrabajos.class, secuencia);
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIASOCONDICIONESTRABAJOS ERROR " + e);
            return null;
        }
    }

    @Override
    public List<SoCondicionesTrabajos> buscarSoCondicionesTrabajos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT soct FROM SoCondicionesTrabajos soct ORDER BY soct.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SoCondicionesTrabajos> listaSOCondicionesTrabajos = query.getResultList();
            return listaSOCondicionesTrabajos;
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR DATOS DE LA ENTIDAD SOCONDICIONESTRABAJOS ERROR " + e);

            return null;
        }
    }

    @Override
    public BigInteger contadorInspecciones(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM inspecciones ins WHERE ins.factorriesgo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.out.println("Contador CONTADORINSPECCIONES persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorInspecciones. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSoAccidentesMedicos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos soa WHERE soa.factorriesgo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.out.println("Contador CONTADORSOACCIDENTESMEDICOS persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSoDetallesPanoramas(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , sodetallespanoramas sop WHERE st.secuencia = sop.condiciontrabajo and sop.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.out.println("Contador CONTADORSODETALLESPANORAMAS persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoDetallesPanoramas. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSoExposicionesFr(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , soexposicionesfr  ser WHERE st.secuencia = ser.indicador and ser.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.out.println("Contador CONTADORSOEXPOSICIONESFR persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoExposicionesFr. " + e);
            return retorno;
        }
    }
}
