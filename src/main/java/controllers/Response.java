package controllers;

public class Response {
    private boolean success;
    private String message;

    // Clase auxiliar de validaciones utilizada en retornos
    // Retorna true en "success" si el metodo fue ejecutado exitosamente.
    // En caso de error, devuelve false, junto a la descripcion del problema
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
