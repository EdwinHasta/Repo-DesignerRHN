/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.RetencionesMinimas;
import Entidades.VigenciasRetencionesMinimas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarRetencionesMinimasInterface {

    public void borrarVigenciaRetencion(VigenciasRetencionesMinimas vretenciones);

    public void crearVigenciaRetencion(VigenciasRetencionesMinimas vretenciones);

    public void modificarVigenciaRetencion(List<VigenciasRetencionesMinimas> listaVigenciasRetencionesModificar);

    public List<VigenciasRetencionesMinimas> consultarVigenciasRetenciones();

    public void borrarRetencion(RetencionesMinimas retenciones);

    public void crearRetencion(RetencionesMinimas retenciones);

    public void modificarRetencion(List<RetencionesMinimas> listaRetencionesModificar);

    public List<RetencionesMinimas> consultarRetenciones(BigInteger secRetencion);

}
