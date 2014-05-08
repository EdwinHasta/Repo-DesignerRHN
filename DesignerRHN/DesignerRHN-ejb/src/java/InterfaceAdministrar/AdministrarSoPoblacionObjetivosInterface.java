/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.SoPoblacionObjetivos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarSoPoblacionObjetivosInterface {

    /**
     * Método encargado de obtener el Entity Manager el cual tiene
     * asociado la sesion del usuario que utiliza el aplicativo.
     * @param idSesion Identificador se la sesion.
     */
    public void obtenerConexion(String idSesion);
    
    public void modificarSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos);

    public void borrarSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos);

    public void crearSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos);

    public List<SoPoblacionObjetivos> consultarSoPoblacionObjetivos();

    public SoPoblacionObjetivos consultarEvalCompetencia(BigInteger secSoPoblacionObjetivo);
}
