/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.DetallesTiposCotizantes;
import InterfacePersistencia.PersistenciaDetallesTiposCotizantesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'DetallesTiposCotizantes' de la
 * base de datos.
 *
 * @author Victor Algarin
 */
@Stateless
public class PersistenciaDetallesTiposCotizantes implements PersistenciaDetallesTiposCotizantesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(DetallesTiposCotizantes detallesTiposCotizantes) {
        try {
            em.persist(detallesTiposCotizantes);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaDetallesTiposCotizantes");
        }
    }

    @Override
    public void editar(DetallesTiposCotizantes detallesTiposCotizantes) {
        try {
            em.merge(detallesTiposCotizantes);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaDetallesTiposCotizantes");
        }
    }

    @Override
    public void borrar(DetallesTiposCotizantes detallesTiposCotizantes) {
        try {
            em.remove(em.merge(detallesTiposCotizantes));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaDetallesTiposCotizantes");
        }
    }
    
    @Override
    public List<DetallesTiposCotizantes> detallesTiposCotizantes(BigInteger tipoCotizante) {
        try {
            Query query = em.createQuery("SELECT d FROM DetallesTiposCotizantes d WHERE d.tipocotizante.secuencia = :tipoCotizante");
            query.setParameter("tipoCotizante", tipoCotizante);
            List<DetallesTiposCotizantes> listaDetalles = query.getResultList();
            return listaDetalles;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaDetallesTiposCotizantes.detallesTiposCotizantes" + e);
            return null;
        }
    }

    
    
    
}
