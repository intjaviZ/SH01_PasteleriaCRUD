package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepository
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Formulario de pedido nuevo (solo CLIENTE). Necesita el catálogo para armar las opciones. */
class NuevoPedidoViewModel(
    private val catalogoRepository: CatalogoRepository,
    private val pedidosRepository: PedidosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NuevoPedidoUiState())
    val uiState: StateFlow<NuevoPedidoUiState> = _uiState.asStateFlow()

    /** El item inicial usa uid 0; los siguientes (items y filas de distribución) parten de aquí. */
    private var siguienteUid = 1

    init {
        cargarCatalogo()
    }

    // ---------------- Catálogo ----------------

    fun cargarCatalogo() {
        _uiState.update { it.copy(cargandoCatalogo = true, errorCatalogo = null) }
        viewModelScope.launch {
            val pasteles = async { catalogoRepository.obtenerPasteles() }
            val rellenos = async { catalogoRepository.obtenerRellenos() }
            val config = async { catalogoRepository.obtenerConfiguracionTamanos() }

            val resultadoPasteles = pasteles.await()
            val resultadoRellenos = rellenos.await()
            val resultadoConfig = config.await()

            val error = listOf(resultadoPasteles, resultadoRellenos, resultadoConfig)
                .firstNotNullOfOrNull { it.exceptionOrNull() }

            _uiState.update {
                it.copy(
                    cargandoCatalogo = false,
                    pasteles = resultadoPasteles.getOrDefault(emptyList()),
                    rellenos = resultadoRellenos.getOrDefault(emptyList()),
                    config = resultadoConfig.getOrNull(),
                    errorCatalogo = error?.mensajeParaUsuario()
                )
            }
        }
    }

    // ---------------- Datos generales ----------------

    fun onFechaSeleccionada(fecha: LocalDate) {
        _uiState.update {
            it.copy(
                fechaEntrega = fecha,
                erroresFecha = it.erroresFecha.copy(fecha = null),
                errorGeneral = null
            )
        }
    }

    fun onHoraSeleccionada(hora: Int) {
        _uiState.update {
            it.copy(
                horaEntrega = hora,
                erroresFecha = it.erroresFecha.copy(hora = null),
                errorGeneral = null
            )
        }
    }

    fun onNotasChange(valor: String) {
        _uiState.update { it.copy(notas = valor, errorGeneral = null) }
    }

    // ---------------- Items ----------------

    fun agregarItem() {
        _uiState.update { it.copy(items = it.items + ItemFormUi(uid = siguienteUid++)) }
    }

    fun quitarItem(uid: Int) {
        _uiState.update { s ->
            if (s.items.size <= 1) s else s.copy(items = s.items.filterNot { it.uid == uid })
        }
    }

    fun onPastelChange(uid: Int, pastelId: Int) {
        val pastel = _uiState.value.pasteles.firstOrNull { it.id == pastelId }
        actualizarItem(uid) { item ->
            item.copy(
                pastelId = pastelId,
                // Si el pastel nuevo no admite relleno, se descarta el que hubiera elegido.
                rellenoId = if (pastel?.permiteRelleno == true) item.rellenoId else null,
                errores = item.errores.copy(pastel = null, relleno = null)
            )
        }
    }

    fun onRellenoChange(uid: Int, rellenoId: Int) {
        actualizarItem(uid) { it.copy(rellenoId = rellenoId, errores = it.errores.copy(relleno = null)) }
    }

    fun onPorcionesChange(uid: Int, porciones: Int) {
        val umbral = _uiState.value.config?.umbralDistribucion ?: Int.MAX_VALUE
        actualizarItem(uid) { item ->
            val sigueDividiendo = item.dividirEnTortas && porciones >= umbral
            item.copy(
                porciones = porciones,
                dividirEnTortas = sigueDividiendo,
                distribucion = if (sigueDividiendo) item.distribucion else emptyList(),
                errores = item.errores.copy(porciones = null, distribucion = null)
            )
        }
    }

    fun onColorPrimarioChange(uid: Int, hex: String) {
        actualizarItem(uid) { it.copy(colorPrimario = hex, errores = it.errores.copy(colorPrimario = null)) }
    }

    fun onColorSecundarioChange(uid: Int, hex: String) {
        actualizarItem(uid) { it.copy(colorSecundario = hex, errores = it.errores.copy(colorSecundario = null)) }
    }

    fun onDedicatoriaChange(uid: Int, valor: String) {
        val maximo = _uiState.value.config?.dedicatoriaMaxCaracteres ?: 50
        actualizarItem(uid) {
            it.copy(dedicatoria = valor.take(maximo), errores = it.errores.copy(dedicatoria = null))
        }
    }

    // ---------------- Distribución de tortas ----------------

    fun onDividirChange(uid: Int, activo: Boolean) {
        actualizarItem(uid) { item ->
            if (activo) {
                item.copy(
                    dividirEnTortas = true,
                    distribucion = item.distribucion.ifEmpty {
                        listOf(FilaDistribucionUi(uid = siguienteUid++, porcionesTorta = item.porciones))
                    }
                )
            } else {
                item.copy(
                    dividirEnTortas = false,
                    distribucion = emptyList(),
                    errores = item.errores.copy(distribucion = null)
                )
            }
        }
    }

    fun agregarFila(uid: Int) {
        actualizarItem(uid) {
            it.copy(
                distribucion = it.distribucion + FilaDistribucionUi(uid = siguienteUid++),
                errores = it.errores.copy(distribucion = null)
            )
        }
    }

    fun quitarFila(uid: Int, filaUid: Int) {
        actualizarItem(uid) {
            it.copy(
                distribucion = it.distribucion.filterNot { fila -> fila.uid == filaUid },
                errores = it.errores.copy(distribucion = null)
            )
        }
    }

    fun onFilaPorcionesChange(uid: Int, filaUid: Int, porciones: Int) {
        actualizarFila(uid, filaUid) { it.copy(porcionesTorta = porciones) }
    }

    fun onFilaCantidadChange(uid: Int, filaUid: Int, texto: String) {
        actualizarFila(uid, filaUid) { it.copy(cantidad = texto.filter { c -> c.isDigit() }.take(2)) }
    }

    // ---------------- Envío ----------------

    fun onEnviarClick() {
        val actual = _uiState.value
        if (actual.enviando) return
        val config = actual.config ?: return
        val zona = ZoneId.systemDefault()

        val erroresFecha = ValidadorNuevoPedido.fechaEntrega(
            fecha = actual.fechaEntrega,
            hora = actual.horaEntrega,
            margenHoras = config.margenMinimoEntregaHoras,
            ahora = Instant.now(),
            zona = zona
        )
        val itemsValidados = actual.items.map { item ->
            val pastel = actual.pasteles.firstOrNull { it.id == item.pastelId }
            item.copy(errores = ValidadorNuevoPedido.item(item, pastel, config))
        }

        if (erroresFecha.hayErrores() || itemsValidados.any { it.errores.hayErrores() }) {
            _uiState.update { it.copy(erroresFecha = erroresFecha, items = itemsValidados) }
            return
        }

        val fecha = actual.fechaEntrega ?: return
        val hora = actual.horaEntrega ?: return
        val nuevoPedido = NuevoPedido(
            fechaEntregaEsperada = fecha.atTime(hora, 0).atZone(zona).toInstant(),
            notasEntrega = actual.notas.trim().ifEmpty { null },
            items = itemsValidados.mapNotNull { ValidadorNuevoPedido.aNuevoItem(it, config) }
        )

        _uiState.update {
            it.copy(enviando = true, errorGeneral = null, erroresFecha = erroresFecha, items = itemsValidados)
        }
        viewModelScope.launch {
            pedidosRepository.crear(nuevoPedido).fold(
                onSuccess = { pedido ->
                    _uiState.update { it.copy(enviando = false, pedidoCreadoId = pedido.id) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(enviando = false, errorGeneral = error.mensajeParaUsuario()) }
                }
            )
        }
    }

    fun consumirPedidoCreado() {
        _uiState.update { it.copy(pedidoCreadoId = null) }
    }

    // ---------------- Utilidades ----------------

    private fun actualizarItem(uid: Int, transformar: (ItemFormUi) -> ItemFormUi) {
        _uiState.update { s ->
            s.copy(
                items = s.items.map { if (it.uid == uid) transformar(it) else it },
                errorGeneral = null
            )
        }
    }

    private fun actualizarFila(uid: Int, filaUid: Int, transformar: (FilaDistribucionUi) -> FilaDistribucionUi) {
        actualizarItem(uid) { item ->
            item.copy(
                distribucion = item.distribucion.map { if (it.uid == filaUid) transformar(it) else it },
                errores = item.errores.copy(distribucion = null)
            )
        }
    }
}
