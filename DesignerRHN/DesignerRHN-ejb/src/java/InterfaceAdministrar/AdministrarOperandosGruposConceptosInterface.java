/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.GruposConceptos;
import Entidades.Operandos;
import Entidades.OperandosGruposConceptos;
import Entidades.Procesos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarOperandosGruposConceptosInterface {
    
    public void borrarProcesos(Procesos procesos);
    
    public void crearProcesos(Procesos procesos);

    public void modificarProcesos(List<Procesos> listaVigenciasRetencionesModificar);

    public List<Procesos> consultarProcesos();
    
// Listas de Valores
    
    public List<Operandos> consultarOperandos();
    
    public List<GruposConceptos> consultarGrupos();

//OperandosGruposConceptos

    public void borrarOperandosGrupos(OperandosGruposConceptos operandos);

    public void crearOperandosGrupos(OperandosGruposConceptos operandos);

    public void modificarOperandosGrupos(List<OperandosGruposConceptos> listaOperandosGruposConceptosModificar);

    public List<OperandosGruposConceptos> consultarOperandosGrupos(BigInteger secProceso);
    
}
