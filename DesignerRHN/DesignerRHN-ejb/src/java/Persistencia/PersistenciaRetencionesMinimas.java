/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.RetencionesMinimas;
import InterfacePersistencia.PersistenciaRetencionesMinimasInterface;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaRetencionesMinimas implements PersistenciaRetencionesMinimasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    //Trae las relaciones en base al ausentismo seleccionado
    @Override
    public List<RetencionesMinimas> retenciones() {
        try {
            Query query = em.createQuery("SELECT u FROM RetencionesMinimas u ORDER BY u.retencion ASC");
            List<RetencionesMinimas> resultado = query.getResultList();
            for (int i = 0; i < resultado.size(); i++) {
                System.out.println("resultado : " + resultado.get(i).getSecuencia());
            }

            return resultado;

        } catch (Exception e) {
            System.out.println("Error: ( RetencionesMinimas)" + e.toString());
            return null;
        }
    }
}
