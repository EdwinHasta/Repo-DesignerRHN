package Persistencia;

import Entidades.Rastros;
import InterfacePersistencia.PersistenciaRastrosInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaRastros implements PersistenciaRastrosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Rastros> rastrosTabla(BigInteger secRegistro, String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT r FROM Rastros r WHERE r.tabla = :nombreTabla AND r.secuenciatabla = :secRegistro AND r.manipulacion IN ('I','U') ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            query.setParameter("secRegistro", secRegistro.toString());
            List<Rastros> listaRastroTabla = query.getResultList();
            return listaRastroTabla;

        } catch (Exception e) {
            System.out.println("Exepcion en rastrosTabla " + e);
            return null;
        }
    }

    public List<Rastros> rastrosTablaHistoricos(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT r FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion IN ('I','U') ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            List<Rastros> listaRastroTabla = query.getResultList();
            return listaRastroTabla;

        } catch (Exception e) {
            System.out.println("Exepcion en rastrosTablaHistoricos " + e);
            return null;
        }
    }

    public List<Rastros> rastrosTablaHistoricosEliminados(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT r FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion = 'D' ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            List<Rastros> listaRastroTabla = query.getResultList();
            return listaRastroTabla;

        } catch (Exception e) {
            System.out.println("Exepcion en rastrosTablaHistoricos " + e);
            return null;
        }
    }

    public List<Rastros> rastrosTablaHistoricosEliminadosEmpleados(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT r FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion = 'D' AND EXISTS (SELECT r FROM RastrosValores rv where rv.nombrecolumna = 'EMPLEADO'AND EXISTS (SELECT t FROM "
                    + nombreTabla + " t WHERE rv.valorprevio = t.empleado.secuencia)) ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            List<Rastros> listaRastroTabla = query.getResultList();
            return listaRastroTabla;

        } catch (Exception e) {
            System.out.println("Exepcion en rastrosTablaHistoricos " + e);
            return null;
        }
    }

    public boolean verificarEmpleadoTabla(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(r) FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion = 'D' AND EXISTS (SELECT r FROM RastrosValores rv where rv.nombrecolumna = 'EMPLEADO'AND EXISTS (SELECT t FROM "
                    + nombreTabla + " t WHERE rv.valorprevio = t.empleado.secuencia)) ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            Long resultado = (Long) query.getSingleResult();
            if (resultado > -1) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<Rastros> rastrosTablaFecha(Date fechaRegistro, String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT r FROM Rastros r WHERE r.tabla = :nombreTabla AND r.fecharastro = :fechaRegistro AND r.manipulacion IN ('I','U') ORDER BY r.fecharastro DESC");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            query.setParameter("fechaRegistro", fechaRegistro);
            List<Rastros> listaRastroTabla = query.getResultList();
            return listaRastroTabla;

        } catch (Exception e) {
            System.out.println("Exepcion en rastrosTablaFecha " + e);
            return null;
        }
    }

    public boolean verificarRastroRegistroTabla(BigInteger secRegistro, String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(r) FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion IN ('I','U') AND r.secuenciatabla = :secRegistro");
            query.setParameter("nombreTabla", nombreTabla.toUpperCase());
            query.setParameter("secRegistro", secRegistro.toString());
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en verificarRastroTabla " + e);
            return false;
        }
    }

    public boolean verificarRastroHistoricoTabla(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(r) FROM Rastros r WHERE r.tabla = :nombreTabla AND r.manipulacion IN ('I','U')");
            query.setParameter("nombreTabla", nombreTabla);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en verificarRastroTabla " + e);
            return false;
        }
    }
}
