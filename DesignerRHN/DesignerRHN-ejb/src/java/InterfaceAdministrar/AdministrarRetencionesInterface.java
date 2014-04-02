/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Retenciones;
import Entidades.VigenciasRetenciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarRetencionesInterface {

    public void borrarVigenciaRetencion(VigenciasRetenciones vretenciones);

    public void crearVigenciaRetencion(VigenciasRetenciones vretenciones);

    public void modificarVigenciaRetencion(List<VigenciasRetenciones> listaVigenciasRetencionesModificar);

    public List<VigenciasRetenciones> consultarVigenciasRetenciones();

    public void borrarRetencion(Retenciones retenciones);

    public void crearRetencion(Retenciones retenciones);

    public void modificarRetencion(List<Retenciones> listaRetencionesModificar);

    public List<Retenciones> consultarRetenciones(BigInteger secRetencion);

}
