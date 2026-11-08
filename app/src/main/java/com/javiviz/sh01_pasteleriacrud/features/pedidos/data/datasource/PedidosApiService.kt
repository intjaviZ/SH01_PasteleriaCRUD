package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CambioEstadoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CancelarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.NuevoPedidoRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PagarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidosPaginadosDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PedidosApiService {

    // Los parámetros null no se agregan a la URL.
    @GET("pedidos")
    suspend fun listar(
        @Query("estado") estado: String?,
        @Query("fecha_desde") fechaDesde: String?,
        @Query("fecha_hasta") fechaHasta: String?,
        @Query("campo_fecha") campoFecha: String?,
        @Query("cliente_id") clienteId: Int?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): PedidosPaginadosDto

    @POST("pedidos")
    suspend fun crear(@Body request: NuevoPedidoRequestDto): PedidoDto

    @GET("pedidos/{id}")
    suspend fun obtenerPorId(@Path("id") id: Int): PedidoDto

    @POST("pedidos/{id}/pagar")
    suspend fun pagar(@Path("id") id: Int, @Body request: PagarRequestDto): CambioEstadoDto

    @POST("pedidos/{id}/entregar")
    suspend fun entregar(@Path("id") id: Int): CambioEstadoDto

    @POST("pedidos/{id}/cancelar")
    suspend fun cancelar(@Path("id") id: Int, @Body request: CancelarRequestDto): CambioEstadoDto
}
