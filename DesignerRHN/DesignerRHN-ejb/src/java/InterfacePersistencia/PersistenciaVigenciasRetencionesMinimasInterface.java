/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasRetencionesMinimas;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasRetencionesMinimasInterface {

    public void crear(VigenciasRetencionesMinimas vretenciones);

    public void editar(VigenciasRetencionesMinimas vretenciones);

    public void borrar(VigenciasRetencionesMinimas vretenciones);

    public List<VigenciasRetencionesMinimas> buscarVigenciasRetencionesMinimas();

}
