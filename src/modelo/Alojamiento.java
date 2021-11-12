
package modelo;

import java.util.Objects;

public class Alojamiento {
   
   private int idAlojamiento;
   private Destino destino;
   private String tipoAlojamiento;
   private float costo;
   private String nombre;
   private boolean activo;

   
   
    public Alojamiento() {
    }

    public Alojamiento(Destino destino, String tipoAlojamiento, float costo, String nombre, boolean activo) {
        this.destino = destino;
        this.tipoAlojamiento = tipoAlojamiento;
        this.costo = costo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Alojamiento(int idAlojamiento, Destino destino, String tipoAlojamiento, float costo, String nombre, boolean activo) {
        this.idAlojamiento = idAlojamiento;
        this.destino = destino;
        this.tipoAlojamiento = tipoAlojamiento;
        this.costo = costo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(int idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public String getTipoAlojamiento() {
        return tipoAlojamiento;
    }

    public void setTipoAlojamiento(String tipoAlojamiento) {
        this.tipoAlojamiento = tipoAlojamiento;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash = 89 * hash + this.idAlojamiento;
        hash = 89 * hash + Objects.hashCode(this.destino);
        hash = 89 * hash + Objects.hashCode(this.tipoAlojamiento);
        hash = 89 * hash + Float.floatToIntBits(this.costo);
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + (this.activo ? 1 : 0);
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
        final Alojamiento other = (Alojamiento) obj;
        if (this.idAlojamiento != other.idAlojamiento) {
            return false;
        }
        if (Float.floatToIntBits(this.costo) != Float.floatToIntBits(other.costo)) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.tipoAlojamiento, other.tipoAlojamiento)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.destino, other.destino)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alojamiento{" + "idAlojamiento=" + idAlojamiento + ", destino=" + destino + ", tipoAlojamiento=" + tipoAlojamiento + ", costo=" + costo + ", nombre=" + nombre + ", activo=" + activo + '}';
    }

   
    
    

   
}
