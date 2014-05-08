/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Diagnosticoscategorias;
import InterfacePersistencia.PersistenciaDiagnosticosCategoriasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DiagnosticosCategorias'
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaDiagnosticosCategorias implements PersistenciaDiagnosticosCategoriasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
   /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,Diagnosticoscategorias diagnosticosCategorias) {
        em.persist(diagnosticosCategorias);
    }

    @Override
    public void editar(EntityManager em,Diagnosticoscategorias diagnosticosCategorias) {
        em.merge(diagnosticosCategorias);
    }

    @Override
    public void borrar(EntityManager em,Diagnosticoscategorias diagnosticosCategorias) {
        em.remove(em.merge(diagnosticosCategorias));
    }

    @Override
    public List<Diagnosticoscategorias> buscarDiagnosticos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT d FROM Diagnosticoscategorias d ORDER BY d.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Diagnosticoscategorias> diagnosticosCategorias = query.getResultList();
            return diagnosticosCategorias;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaDiagnosticosCategorias buscarDiagnosticos ERROR" + e);
            return null;
        }
    }
}
