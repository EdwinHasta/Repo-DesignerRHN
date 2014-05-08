/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.NovedadesSistema;
import InterfacePersistencia.PersistenciaNovedadesSistemaInterface;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'NovedadesSistema' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaNovedadesSistema implements PersistenciaNovedadesSistemaInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, NovedadesSistema novedades) {
        try {
            em.merge(novedades);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaNovedades.crear");
        }
    }

    @Override
    public void editar(EntityManager em, NovedadesSistema novedades) {
        em.merge(novedades);
    }

    @Override
    public void borrar(EntityManager em, NovedadesSistema novedades) {
        em.remove(em.merge(novedades));
    }

    @Override
    public List<NovedadesSistema> novedadesEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT n FROM NovedadesSistema n WHERE n.tipo = 'DEFINITIVA' and n.empleado.secuencia = :secuenciaEmpleado ORDER BY n.fechainicialdisfrute DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<NovedadesSistema> novedadesSistema = query.getResultList();
            return novedadesSistema;
        } catch (Exception e) {
            System.out.println("Error: (novedadesEmpleado)" + e);
            return null;
        }
    }

    public List<NovedadesSistema> novedadesEmpleadoVacaciones(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT n FROM NovedadesSistema n WHERE n.empleado.secuencia = :secuenciaEmpleado ORDER BY n.fechainicialdisfrute DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<NovedadesSistema> novedadesSistema = query.getResultList();
            return novedadesSistema;
        } catch (Exception e) {
            System.out.println("Error: (novedadesEmpleado)" + e);
            return null;
        }
    }

    public String buscarEstadoVacaciones(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date ultimaFechaDisfrute, fechaRegreso;
            try {
                Query query = em.createQuery("SELECT MAX(ns.fechainicialdisfrute) FROM NovedadesSistema ns WHERE ns.empleado.secuencia = :secuenciaEmpleado AND ns.tipo = 'VACACION'");
                query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ultimaFechaDisfrute = (Date) query.getSingleResult();
            } catch (Exception e) {
                System.out.println("Error: (buscarEstadoVacaciones - Fecha inicial ultimo disfrute \n)" + e);
                ultimaFechaDisfrute = null;
            }
            try {
                if (ultimaFechaDisfrute != null) {
                    Query query = em.createNativeQuery("SELECT MAX(FECHASIGUIENTEFINVACA)-1 FROM NovedadesSistema ns WHERE ns.empleado = ? AND ns.tipo = 'VACACION' AND ns.fechainicialdisfrute = TO_DATE( ? ,'dd/mm/yyyy')");
                    query.setParameter(1, secuenciaEmpleado);
                    query.setParameter(2, formato.format(ultimaFechaDisfrute));
                    fechaRegreso = (Date) query.getSingleResult();
                } else {
                    fechaRegreso = null;
                }
            } catch (Exception e) {
                System.out.println("Error: (buscarEstadoVacaciones) Fecha regreso ultimo disfrute \n" + e);
                fechaRegreso = null;
            }

            if (fechaRegreso == null) {
                return "SIN VACACIONES DISFRUTADAS";
            } else {
                if (ultimaFechaDisfrute != null) {
                    return "Disfr. " + formato.format(ultimaFechaDisfrute) + " > " + formato.format(fechaRegreso);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: (novedadesEmpleado)" + e);
            return null;
        }
    }
}
