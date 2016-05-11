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
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'HvReferencias' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHvReferencias implements PersistenciaHvReferenciasInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     *
     * @param em
     */
    @Override
    public void crear(EntityManager em, HvReferencias hvReferencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            System.out.println("pass");
            tx.begin();
            em.persist(hvReferencias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvReferencias.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, HvReferencias hvReferencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(hvReferencias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvReferencias.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, HvReferencias hvReferencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(hvReferencias));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaHvReferencias.borrar: " + e);
            }
        }
    }

    @Override
    public List<HvReferencias> buscarHvReferencias(EntityManager em) {
        em.clear();
        Query query = em.createQuery("SELECT te FROM HvEntrevistas te ORDER BY te.fecha ASC ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<HvReferencias> listHvReferencias = query.getResultList();
        return listHvReferencias;

    }

    @Override
    public List<HvReferencias> consultarHvReferenciasPersonalesPorPersona(EntityManager em, BigInteger secPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT hr FROM HvReferencias hr , Personas e WHERE e.secuencia = hr.hojadevida.persona.secuencia AND e.secuencia = :secuenciaEmpl AND hr.tipo='PERSONALES' ORDER BY hr.nombrepersona ");
            query.setParameter("secuenciaEmpl", secPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HvReferencias> listHvReferencias = query.getResultList();
            if (listHvReferencias != null) {
                System.out.println("PersistenciaHvReferencias Tamaño listHvReferencias : " + listHvReferencias.size());
            }
            return listHvReferencias;
        } catch (Exception e) {
            System.err.println("Error en Persistencia Vigencias Normas Empleados " + e);
            return null;
        }
    }

    @Override
    public List<HvReferencias> referenciasPersonalesPersona(EntityManager em, BigInteger secuenciaHV) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'PERSONALES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
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
    public List<HVHojasDeVida> consultarHvHojaDeVidaPorPersona(EntityManager em, BigInteger secEmpleado) {
        try {
            em.clear();
            System.out.println("PersistenciaHvReferencias secuencia empleado hoja de vida " + secEmpleado);
            Query query = em.createQuery("SELECT hv FROM HVHojasDeVida hv , Personas e WHERE e.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HVHojasDeVida> hvHojasDeVIda = query.getResultList();
            return hvHojasDeVIda;
        } catch (Exception e) {
            System.err.println("Error en Persistencia HVREFERENCIAS buscarHvHojaDeVidaPorPersona " + e);
            return null;
        }
    }

    @Override
    public List<HvReferencias> consultarHvReferenciasFamiliarPorPersona(EntityManager em, BigInteger secEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT hr FROM  HvReferencias hr , Personas e WHERE e.secuencia  = hr.hojadevida.persona.secuencia AND e.secuencia = :secuenciaEmpl AND hr.tipo='FAMILIARES' ORDER BY hr.nombrepersona ");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HvReferencias> listHvReferencias = query.getResultList();
            return listHvReferencias;
        } catch (Exception e) {
            System.out.println("Error en Persistencia HvRefencias 1  " + e);
            return null;
        }
    }

    @Override
    public HvReferencias buscarHvReferencia(EntityManager em, BigInteger secHvReferencias) {
        try {
            em.clear();
            return em.find(HvReferencias.class, secHvReferencias);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<HvReferencias> contarReferenciasFamiliaresPersona(EntityManager em, BigInteger secuenciaHV) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'FAMILIARES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
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
