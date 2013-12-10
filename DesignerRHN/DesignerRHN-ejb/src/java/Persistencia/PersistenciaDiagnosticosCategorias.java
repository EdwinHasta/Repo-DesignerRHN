/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'DiagnosticosCategorias'
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaDiagnosticosCategorias implements PersistenciaDiagnosticosCategoriasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Diagnosticoscategorias diagnosticosCategorias) {
        em.persist(diagnosticosCategorias);
    }

    @Override
    public void editar(Diagnosticoscategorias diagnosticosCategorias) {
        em.merge(diagnosticosCategorias);
    }

    @Override
    public void borrar(Diagnosticoscategorias diagnosticosCategorias) {
        em.remove(em.merge(diagnosticosCategorias));
    }

    @Override
    public List<Diagnosticoscategorias> buscarDiagnosticos() {
        try {
            Query query = em.createQuery("SELECT d FROM Diagnosticoscategorias d ORDER BY d.codigo DESC");
            List<Diagnosticoscategorias> diagnosticosCategorias = query.getResultList();
            return diagnosticosCategorias;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaDiagnosticosCategorias buscarDiagnosticos ERROR" + e);
            return null;
        }
    }
}
