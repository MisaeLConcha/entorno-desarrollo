## Pruebas Unitarias y Cobertura de Reglas de Negocio

### Reglas de Negocio Críticas del Servicio de Productos
1. Validación de Stock: No se puede vender un producto si el stock es 0.
2. Cálculo de Precio: El precio con IVA debe ser el 19% adicional al precio base.
3. Límite de Compra: Un cliente no puede comprar más de 10 unidades del mismo producto.

### Cobertura Actual
| Regla                  | Estado          | Casos Cubiertos                              |
|------------------------|-----------------|----------------------------------------------|
| 1. Validación de Stock | ✅ Cubierta     | Stock suficiente (feliz), Stock 0 (error)    |
| 2. Cálculo de Precio   | ✅ Cubierta     | 1 producto, múltiples productos              |
| 3. Límite de Compra    | ⚠️ Pendiente   | Solo caso feliz (< 10 unidades)               |

### Reflexión y Deuda Técnica
- Riesgo sin probar: La regla de límite de compra no tiene test de caso de error.
- Acción Futura: Agregar test para el caso de error de la Regla 3 (pedido de 11+ unidades).
- Responsable: Equipo Backend · Sprint 4
