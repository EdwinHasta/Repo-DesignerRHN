/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TempNovedades;
import InterfacePersistencia.PersistenciaTempNovedadesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'TempNovedades'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTempNovedades implements PersistenciaTempNovedadesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TempNovedades tempNovedades) {
        em.persist(tempNovedades);
    }

    @Override
    public void editar(TempNovedades tempNovedades) {
        em.merge(tempNovedades);
    }

    @Override
    public void borrarRegistrosTempNovedades(String usuarioBD) {
        try {
            Query query = em.createQuery("DELETE FROM TempNovedades t "
                                       + "WHERE t.usuariobd = :usuarioBD AND t.estado = 'N'");
            query.setParameter("usuarioBD", usuarioBD);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro.");
        }
    }

    @Override
    public List<TempNovedades> obtenerTempNovedades(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT t FROM TempNovedades t "
                                       + "WHERE t.usuariobd = :usuarioBD AND t.estado = 'N'");
            query.setParameter("usuarioBD", usuarioBD);
            List<TempNovedades> listTNovedades = query.getResultList();
            return listTNovedades;
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro.");
            return null;
        }
    }

    @Override
    public List<String> obtenerDocumentosSoporteCargados(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT t.documentosoporte FROM TempNovedades t "
                                       + "WHERE t.usuariobd = :usuarioBD AND t.estado = 'C'");
            query.setParameter("usuarioBD", usuarioBD);
            List<String> listDocumentosSoporte = query.getResultList();
            return listDocumentosSoporte;
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro.");
            return null;
        }
    }

    @Override
    public void cargarTempNovedades(String fechaReporte, String nombreCortoFormula, String usarFormula) {
        try {
            String sqlQuery = "call TEMPNOVEDADES_PKG.INSERTARNOVEDAD(To_date(?, 'dd/mm/yyyy'), ?, ?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, fechaReporte);
            query.setParameter(2, nombreCortoFormula);
            query.setParameter(3, usarFormula);
            query.executeUpdate();
            System.out.println("executeUpdate: ");
        } catch (Exception e) {
            System.out.println("Error en el carge." + e);
        }
    }

    @Override
    public void reversarTempNovedades(String usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("DELETE FROM TempNovedades t WHERE t.usuariobd = :usuarioBD "
                                                                    + "AND t.estado = 'C' "
                                                                    + "AND t.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro.");
        }
    }
}
