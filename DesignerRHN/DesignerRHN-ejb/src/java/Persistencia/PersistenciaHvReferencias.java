/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import InterfacePersistencia.PersistenciaHvReferenciasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'HvReferencias'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHvReferencias implements PersistenciaHvReferenciasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(HvReferencias hvReferencias) {
        em.persist(hvReferencias);
    }

    public void editar(HvReferencias hvReferencias) {
        em.merge(hvReferencias);
    }

    public void borrar(HvReferencias hvReferencias) {
        em.remove(em.merge(hvReferencias));
    }

    public HvReferencias buscarHvReferencia(BigInteger secuenciaHvReferencias) {
        try {
            return em.find(HvReferencias.class, secuenciaHvReferencias);
        } catch (Exception e) {
            return null;
        }
    }

    public List<HvReferencias> buscarHvReferencias() {
        Query query = em.createQuery("SELECT te FROM HvEntrevistas te ORDER BY te.fecha ASC ");
        List<HvReferencias> listHvReferencias = query.getResultList();
        return listHvReferencias;

    }

    public List<HvReferencias> buscarHvReferenciasPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT hr FROM HVHojasDeVida hv , HvReferencias hr , Empleados e WHERE hv.secuencia = hr.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl AND hr.tipo='PERSONALES' ORDER BY hr.nombrepersona ");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HvReferencias> listHvReferencias = query.getResultList();
            return listHvReferencias;
        } catch (Exception e) {
            System.err.println("Error en Persistencia Vigencias Normas Empleados " + e);
            return null;
        }
    }

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado) {
        try {
            System.out.println("PersistenciaHvReferencias secuencia empleado hoja de vida " + secEmpleado);
            Query query = em.createQuery("SELECT hv FROM HVHojasDeVida hv , HvReferencias hr , Empleados e WHERE hv.secuencia = hr.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HVHojasDeVida> hvHojasDeVIda = query.getResultList();
            return hvHojasDeVIda;
        } catch (Exception e) {
            System.err.println("Error en Persistencia HVREFERENCIAS buscarHvHojaDeVidaPorEmpleado " + e);
            return null;
        }
    }
    
    @Override
    public List<HvReferencias> referenciasPersonalesPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'PERSONALES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvReferencias> listaReferenciasPersonales = queryFinal.getResultList();
                return listaReferenciasPersonales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciasHvReferencias.referenciasPersonalesPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<HvReferencias> referenciasFamiliaresPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'FAMILIARES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvReferencias> listaReferenciasPersonales = queryFinal.getResultList();
                return listaReferenciasPersonales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciasHvReferencias.referenciasPersonalesPersona" + e);
            return null;
        }
    }
}
