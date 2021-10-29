
package modelo;

import java.util.Objects;


public class Destino {
   
   int idDestino;
   String nombre;
   String pais;
   boolean activo;

    public Destino() {
    }

    public Destino(String nombre, String pais, boolean activo) {
        this.nombre = nombre;
        this.pais = pais;
        this.activo = activo;
    }

    public Destino(int idDestino, String nombre, String pais, boolean activo) {
        this.idDestino = idDestino;
        this.nombre = nombre;
        this.pais = pais;
        this.activo = activo;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.idDestino;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.pais);
        hash = 13 * hash + (this.activo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Destino other = (Destino) obj;
        if (this.idDestino != other.idDestino) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Destino{" + "idDestino=" + idDestino + ", nombre=" + nombre + ", pais=" + pais + ", activo=" + activo + '}';
    }
   
   
    
    
}
