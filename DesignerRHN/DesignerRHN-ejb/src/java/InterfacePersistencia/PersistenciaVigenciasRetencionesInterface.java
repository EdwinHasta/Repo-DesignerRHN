/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasRetenciones;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasRetencionesInterface {

    public void crear(EntityManager em, VigenciasRetenciones vretenciones);

    public void editar(EntityManager em, VigenciasRetenciones vretenciones);

    public void borrar(EntityManager em, VigenciasRetenciones vretenciones);

    public List<VigenciasRetenciones> buscarVigenciasRetenciones(EntityManager em );

}
