/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposCertificados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposCertificadosInterface {

    /**
     * Método encargado de modificar TiposCertificados.
     *
     * @param listaTiposCertificados Lista TiposCertificados que se van a modificar.
     */
    public void modificarTiposCertificados(List<TiposCertificados> listaTiposCertificados);

    /**
     * Método encargado de borrar TiposCertificados.
     *
     * @param listaTiposCertificados Lista TiposCertificados que se van a borrar.
     */
    public void borrarTiposCertificados(List<TiposCertificados> listaTiposCertificados);

    /**
     * Método encargado de crear TiposCertificados.
     *
     * @param listaTiposCertificados Lista TiposCertificados que se van a crear.
     */
    public void crearTiposCertificados(List<TiposCertificados> listaTiposCertificados);

    /**
     * Método encargado de recuperar las TiposCertificados para una tabla de la
     * pantalla.
     *
     * @return Retorna una lista de TiposCertificados.
     */
    public List<TiposCertificados> consultarTiposCertificados();

    /**
     * Método encargado de recuperar un TipoCertificado dada su secuencia.
     *
     * @param secTiposCertificados Secuencia del TipoCertificado
     * @return Retorna un TipoCertificado.
     */
    public TiposCertificados consultarTipoCertificado(BigInteger secTiposCertificados);
}
