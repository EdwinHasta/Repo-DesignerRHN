/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposDocumentos;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosContratos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposDocumentos implements PersistenciaTiposDocumentosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<TiposDocumentos> tiposDocumentos() {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDocumentos td ORDER BY td.nombrecorto");
            List<TiposDocumentos> listaTiposDocumentos = query.getResultList();
            return listaTiposDocumentos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDocumentos.ciudades " + e);
            return null;
        }
    }
}
