/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposFunciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTiposFuncionesInterface {

    public List<TiposFunciones> buscarTiposFunciones(BigInteger secuenciaOperando, String tipoOperando);

    public void borrarTiposFunciones(TiposFunciones tiposFunciones);

    public void crearTiposFunciones(TiposFunciones tiposFunciones);

    public void modificarTiposFunciones(List<TiposFunciones> listaTiposFuncionesModificar);

}
