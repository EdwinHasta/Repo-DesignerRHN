/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Novedades;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Novedades' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaNovedades implements PersistenciaNovedadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Novedades novedades) {
        try {
            em.persist(novedades);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaNovedades.crear");
        }
    }

    @Override
    public void editar(Novedades novedades) {
        em.merge(novedades);
    }

    @Override
    public void borrar(Novedades novedades) {
        em.remove(em.merge(novedades));
    }

    @Override
    public Novedades buscarNovedad(BigInteger secNovedad) {
        try {
            if (secNovedad != null) {
                Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.secuencia = :secNovedad");
                query.setParameter("secNovedad", secNovedad);
                Novedades novedad = (Novedades) query.getSingleResult();
                return novedad;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error buscarNovedad PersistenciaNovedades : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Novedades> novedadesParaReversar(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            List<Novedades> listNovedades = query.getResultList();
            return listNovedades;
        } catch (Exception e) {
            System.out.println("Error: (novedadesParaReversar)" + e);
            return null;
        }
    }

    @Override
    public List<Novedades> todasNovedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Novedades> todasNovedades = query.getResultList();
            return todasNovedades;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }

    @Override
    public List<Novedades> todasNovedadesConcepto(BigInteger secuenciaConcepto) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.concepto.secuencia= :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            List<Novedades> todasNovedades = query.getResultList();
            return todasNovedades;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedadesConcepto)" + e);
            return null;
        }
    }

    @Override
    public List<Novedades> todasNovedadesTercero(BigInteger secuenciaTercero) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.tercero.secuencia= :secuenciaTercero");
            query.setParameter("secuenciaTercero", secuenciaTercero);
            List<Novedades> todasNovedades = query.getResultList();
            return todasNovedades;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedadesTercero)" + e);
            return null;
        }
    }

    @Override
    public int reversarNovedades(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("DELETE FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            int rows = query.executeUpdate();
            return rows;
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro. (reversarNovedades)" + e);
            return 0;
        }
    }

    @Override
    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            List<Novedades> listaNovedades;
            String sqlQuery = "SELECT * FROM Novedades n WHERE  n.empleado = ? AND n.tipo IN ('FIJA','PAGO POR FUERA','OCASIONAL') and ((n.fechafinal IS NULL AND NVL(SALDO,99999) > 0) OR (n.saldo > 0 and n.fechainicial >= (SELECT fechadesdecausado FROM vwactualesfechas)) OR n.fechafinal > (SELECT fechadesdecausado FROM vwactualesfechas )) ORDER BY n.fechafinal";
            Query query = em.createNativeQuery(sqlQuery, Novedades.class);
            query.setParameter(1, secuenciaEmpleado);
            listaNovedades = query.getResultList();
            return listaNovedades;
        } catch (Exception e) {
            System.out.println("Error PersistenciaNovedades.novedadesEmpleado" + e);
            return null;
        }
    }
}
