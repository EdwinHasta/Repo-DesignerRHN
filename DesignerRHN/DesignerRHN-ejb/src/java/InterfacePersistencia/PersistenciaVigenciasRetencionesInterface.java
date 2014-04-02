/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasRetenciones;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasRetencionesInterface {

    public void crear(VigenciasRetenciones vretenciones);

    public void editar(VigenciasRetenciones vretenciones);

    public void borrar(VigenciasRetenciones vretenciones);

    public List<VigenciasRetenciones> buscarVigenciasRetenciones();

}
