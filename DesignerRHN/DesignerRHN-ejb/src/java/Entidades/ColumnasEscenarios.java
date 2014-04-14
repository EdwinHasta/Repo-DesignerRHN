/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "COLUMNASESCENARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColumnasEscenarios.findAll", query = "SELECT c FROM ColumnasEscenarios c"),
    @NamedQuery(name = "ColumnasEscenarios.findBySecuencia", query = "SELECT c FROM ColumnasEscenarios c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "ColumnasEscenarios.findByDescripcion", query = "SELECT c FROM ColumnasEscenarios c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ColumnasEscenarios.findByNombrecolumna", query = "SELECT c FROM ColumnasEscenarios c WHERE c.nombrecolumna = :nombrecolumna"),
    @NamedQuery(name = "ColumnasEscenarios.findByTipo", query = "SELECT c FROM ColumnasEscenarios c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ColumnasEscenarios.findByTamano", query = "SELECT c FROM ColumnasEscenarios c WHERE c.tamano = :tamano"),
    @NamedQuery(name = "ColumnasEscenarios.findByColPrecision", query = "SELECT c FROM ColumnasEscenarios c WHERE c.colPrecision = :colPrecision"),
    @NamedQuery(name = "ColumnasEscenarios.findByLovcolumna1", query = "SELECT c FROM ColumnasEscenarios c WHERE c.lovcolumna1 = :lovcolumna1"),
    @NamedQuery(name = "ColumnasEscenarios.findByLovcolumna2", query = "SELECT c FROM ColumnasEscenarios c WHERE c.lovcolumna2 = :lovcolumna2"),
    @NamedQuery(name = "ColumnasEscenarios.findByLovfrom", query = "SELECT c FROM ColumnasEscenarios c WHERE c.lovfrom = :lovfrom"),
    @NamedQuery(name = "ColumnasEscenarios.findByClasecondicion", query = "SELECT c FROM ColumnasEscenarios c WHERE c.clasecondicion = :clasecondicion"),
    @NamedQuery(name = "ColumnasEscenarios.findByOperadorespecial", query = "SELECT c FROM ColumnasEscenarios c WHERE c.operadorespecial = :operadorespecial"),
    @NamedQuery(name = "ColumnasEscenarios.findBySubqueryespecial", query = "SELECT c FROM ColumnasEscenarios c WHERE c.subqueryespecial = :subqueryespecial")})
public class ColumnasEscenarios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "NOMBRECOLUMNA")
    private String nombrecolumna;
    @Size(max = 106)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "TAMANO")
    private BigInteger tamano;
    @Column(name = "COL_PRECISION")
    private BigInteger colPrecision;
    @Size(max = 500)
    @Column(name = "LOVCOLUMNA1")
    private String lovcolumna1;
    @Size(max = 500)
    @Column(name = "LOVCOLUMNA2")
    private String lovcolumna2;
    @Size(max = 2000)
    @Column(name = "LOVFROM")
    private String lovfrom;
    @Size(max = 10)
    @Column(name = "CLASECONDICION")
    private String clasecondicion;
    @Size(max = 20)
    @Column(name = "OPERADORESPECIAL")
    private String operadorespecial;
    @Size(max = 500)
    @Column(name = "SUBQUERYESPECIAL")
    private String subqueryespecial;
    @JoinColumn(name = "ESCENARIO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Escenarios escenario;

    public ColumnasEscenarios() {
    }

    public ColumnasEscenarios(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombrecolumna() {
        return nombrecolumna;
    }

    public void setNombrecolumna(String nombrecolumna) {
        this.nombrecolumna = nombrecolumna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getTamano() {
        return tamano;
    }

    public void setTamano(BigInteger tamano) {
        this.tamano = tamano;
    }

    public BigInteger getColPrecision() {
        return colPrecision;
    }

    public void setColPrecision(BigInteger colPrecision) {
        this.colPrecision = colPrecision;
    }

    public String getLovcolumna1() {
        return lovcolumna1;
    }

    public void setLovcolumna1(String lovcolumna1) {
        this.lovcolumna1 = lovcolumna1;
    }

    public String getLovcolumna2() {
        return lovcolumna2;
    }

    public void setLovcolumna2(String lovcolumna2) {
        this.lovcolumna2 = lovcolumna2;
    }

    public String getLovfrom() {
        return lovfrom;
    }

    public void setLovfrom(String lovfrom) {
        this.lovfrom = lovfrom;
    }

    public String getClasecondicion() {
        return clasecondicion;
    }

    public void setClasecondicion(String clasecondicion) {
        this.clasecondicion = clasecondicion;
    }

    public String getOperadorespecial() {
        return operadorespecial;
    }

    public void setOperadorespecial(String operadorespecial) {
        this.operadorespecial = operadorespecial;
    }

    public String getSubqueryespecial() {
        return subqueryespecial;
    }

    public void setSubqueryespecial(String subqueryespecial) {
        this.subqueryespecial = subqueryespecial;
    }

    public Escenarios getEscenario() {
        return escenario;
    }

    public void setEscenario(Escenarios escenario) {
        this.escenario = escenario;
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
        if (!(object instanceof ColumnasEscenarios)) {
            return false;
        }
        ColumnasEscenarios other = (ColumnasEscenarios) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ColumnasEscenarios[ secuencia=" + secuencia + " ]";
    }
    
}
