/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.Inforeportes;
import Entidades.Modulos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarInforeportesInterface {
    
    public List<Inforeportes> inforeportes();
    public List<Modulos> lovmodulos();
    public void crearInforeporte(Inforeportes inforeportes);
    public void borrarInforeporte(Inforeportes inforeportes);
    public void modificarInforeporte(List<Inforeportes> listaInforeportesModificar);
    
    
}
