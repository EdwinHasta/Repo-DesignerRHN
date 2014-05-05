/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.ActualUsuario;

/**
 *
 * @author Administrador
 */
public interface AdministrarTemplateInterface {

    public boolean obtenerConexion(String idSesion);

    public ActualUsuario consultarActualUsuario();

}
