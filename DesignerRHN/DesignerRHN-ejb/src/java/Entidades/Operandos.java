/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "OPERANDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operandos.findAll", query = "SELECT o FROM Operandos o")})
public class Operandos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operando")
    private Collection<OperandosLogs> operandosLogsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operando")
    private Collection<ConceptosSoportes> conceptosSoportesCollection;
    @OneToMany(mappedBy = "operandobaseliquidacion")
    private Collection<GruposProvisiones> gruposProvisionesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "VALORREAL")
    private BigDecimal valorreal;
    @Column(name = "VALORDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valordate;
    @Size(max = 100)
    @Column(name = "VALORSTRING")
    private String valorstring;
    @Size(max = 1)
    @Column(name = "CAMBIOANUAL")
    private String cambioanual;
    @Size(max = 1)
    @Column(name = "ACTUALIZABLE")
    private String actualizable;
    @OneToMany(mappedBy = "operando")
    private Collection<Nodos> nodosCollection;
    @Transient
    private String estadoTipo;
    @Transient
    private boolean estadoCambio;
    @Transient
    private boolean estadoActualizable;

    public Operandos() {
    }

    public Operandos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Operandos(BigInteger secuencia, int codigo, String descripcion, String nombre, String tipo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion.toUpperCase();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        if (tipo == null) {
            tipo = "CONSTANTE";
        }
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstadoTipo() {
        if (estadoTipo == null) {
            if (tipo == null) {
                estadoTipo = "CONSTANTE";

            } else {

                if (tipo.equalsIgnoreCase("FORMULA")) {
                    estadoTipo = "FORMULA";
                } else if (tipo.equalsIgnoreCase("BLOQUE PL/SQL")) {
                    estadoTipo = "BLOQUE PL/SQL";
                } else if (tipo.equalsIgnoreCase("CONSTANTE")) {
                    estadoTipo = "CONSTANTE";
                } else if (tipo.equalsIgnoreCase("FUNCION")) {
                    estadoTipo = "FUNCION";
                } else if (tipo.equalsIgnoreCase("RELACIONAL")) {
                    estadoTipo = "RELACIONAL";
                } else if (tipo.equalsIgnoreCase("CONSTANTE")) {
                    estadoTipo = "CONSTANTE";
                }
            }
        }
        return estadoTipo;
    }

    public void setEstadoTipo(String estadoTipo) {

        if (estadoTipo.equals("")) {
            setTipo("CONSTANTE");
        } else if (estadoTipo.equalsIgnoreCase("BLOQUE PL/SQL")) {
            setTipo("BLOQUE PL/SQL");
        } else if (estadoTipo.equals("CONSTANTE")) {
            setTipo("CONSTANTE");
        } else if (estadoTipo.equals("FUNCION")) {
            setTipo("FUNCION");
        } else if (estadoTipo.equals("RELACIONAL")) {
            setTipo("RELACIONAL");
        } else if (estadoTipo.equals("FORMULA")) {
            setTipo("FORMULA");
        }

        this.estadoTipo = estadoTipo;
    }

    public BigDecimal getValorreal() {
        return valorreal;
    }

    public void setValorreal(BigDecimal valorreal) {
        this.valorreal = valorreal;
    }

    public Date getValordate() {
        return valordate;
    }

    public void setValordate(Date valordate) {
        this.valordate = valordate;
    }

    public String getValorstring() {
        return valorstring;
    }

    public void setValorstring(String valorstring) {
        this.valorstring = valorstring;
    }

    public String getCambioanual() {
        return cambioanual;
    }

    public void setCambioanual(String cambioanual) {
        this.cambioanual = cambioanual;
    }

    public String getActualizable() {
        return actualizable;
    }

    public void setActualizable(String actualizable) {
        this.actualizable = actualizable;
    }

    public boolean isEstadoCambio() {
        if (cambioanual != null) {
            if (cambioanual.equals("S")) {
                estadoCambio = true;
            } else {
                estadoCambio = false;
            }
        } else {
            estadoCambio = false;
        }
        return estadoCambio;
    }

    public void setEstadoCambio(boolean estadoCambio) {
        if (estadoCambio == true) {
            cambioanual = "S";
        } else {
            cambioanual = "N";
        }
        this.estadoCambio = estadoCambio;
    }

    public boolean isEstadoActualizable() {
        if (actualizable != null) {
            if (actualizable.equals("S")) {
                estadoActualizable = true;
            } else {
                estadoActualizable = false;
            }
        } else {
            estadoActualizable = false;
        }
        return estadoActualizable;
    }

    public void setEstadoActualizable(boolean estadoActualizable) {
        if (estadoActualizable == true) {
            actualizable = "S";
        } else {
            actualizable = "N";
        }
        this.estadoActualizable = estadoActualizable;
    }

    @XmlTransient
    public Collection<Nodos> getNodosCollection() {
        return nodosCollection;
    }

    public void setNodosCollection(Collection<Nodos> nodosCollection) {
        this.nodosCollection = nodosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operandos)) {
            return false;
        }
        Operandos other = (Operandos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Operandos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection() {
        return gruposProvisionesCollection;
    }

    public void setGruposProvisionesCollection(Collection<GruposProvisiones> gruposProvisionesCollection) {
        this.gruposProvisionesCollection = gruposProvisionesCollection;
    }

    @XmlTransient
    public Collection<ConceptosSoportes> getConceptosSoportesCollection() {
        return conceptosSoportesCollection;
    }

    public void setConceptosSoportesCollection(Collection<ConceptosSoportes> conceptosSoportesCollection) {
        this.conceptosSoportesCollection = conceptosSoportesCollection;
    }

    @XmlTransient
    public Collection<OperandosLogs> getOperandosLogsCollection() {
        return operandosLogsCollection;
    }

    public void setOperandosLogsCollection(Collection<OperandosLogs> operandosLogsCollection) {
        this.operandosLogsCollection = operandosLogsCollection;
    }

}
