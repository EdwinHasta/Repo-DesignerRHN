/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Operandos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarOperandosInterface {

    public List<Operandos> buscarOperandos();

    public void borrarOperando(Operandos operandos);
    public void crearOperando(Operandos operandos);
    public void modificarOperando(List<Operandos> listaOperandosModificar);
    
}
