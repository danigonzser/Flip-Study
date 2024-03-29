package com.example.flipstudy.statistics

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flipstudy.label.data.Label
import com.example.flipstudy.label.data.LabelDatabase
import com.example.flipstudy.label.data.colorEnumToColor
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.util.Calendar
import java.util.Collections.addAll

/**
 * Clase viewModel de la pantalla de estadísticas.
 *
 * @property db Acceso a la base de datos de [Statistic]
 *
 */
class StatisticViewModel(db: LabelDatabase) : ViewModel() {

    private var _model = MutableLiveData<ChartEntryModel>()
    val model : LiveData<ChartEntryModel> = _model

    private var _colors = MutableLiveData<MutableList<Color>>()
    val colors : LiveData<MutableList<Color>> = _colors

    private var _colorsLabelled = MutableLiveData<MutableList<String>>()
    val colorsLabelled : LiveData<MutableList<String>> = _colorsLabelled

    val goalsSameWeek = mutableListOf<Statistic>()

    var columns = mutableListOf<Color>()
    val stringColumns = mutableListOf<String>()

    val customDaysOfWeek = listOf(
        Calendar.MONDAY,
        Calendar.TUESDAY,
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY,
        Calendar.SATURDAY,
        Calendar.SUNDAY
    )

    init {

        Log.d("STATISTICS", "Entramos en INIT")

        val entries = mutableMapOf<Int, MutableList<Float>>()

        val reals =
            mutableStateListOf<Statistic>().apply {
                addAll(db.statisticDao().getAllStatistics())
            }

        reals.forEach{
            Log.d("STATISTICS", "DEBUG REALS -> " + it.id.toString())
        }

        val labels = mutableStateListOf<Label>().apply {
            addAll(db.labelDao().getAllLabels())
        }

        labels.forEach{
            Log.d("STATISTICS", "DEBUG LABELS -> " + it.name)
        }

        reals.forEach {
                statistic ->
            Log.d("STATISTICS", "${statistic.id.toString()} COMPROBANDO")
            if(DateUtils.mismaSemana(System.currentTimeMillis(), statistic.timestamp)){
                goalsSameWeek.add(statistic)
                Log.d("STATISTICS", "${statistic.id.toString()} misma semana")
            }

        }

        Log.d("STATISTICS", "GOALS SAME WEEK SIZE-> " + goalsSameWeek.size)

        goalsSameWeek.forEach {
            Log.d("STATISTICS", "DEBUG GOALS SAME WEEK -> " + it.labelId)
        }

        val statisticsByLabelId = goalsSameWeek.groupBy { it.labelId }

        statisticsByLabelId.forEach {
            (labelId, stats) ->
            Log.d("STATISTICS" ,"Label Id" + labelId.toString())
            labels.forEach {
                label ->
                Log.d("STATISTICS" ,"Label Id(DB)" +label.id.toString())
                if(labelId == label.id){
                    columns.add( colorEnumToColor(label.color) )
                    Log.d("STATISTICS" ,"Label Id(DB)" +label.id.toString() + "Label Id" + labelId.toString() + colorEnumToColor(label.color).toString())
                }
            }
        }

        Log.d("STATISTICS", "Valor columns" + columns.toString())

        statisticsByLabelId.forEach {
                (labelId, stats) ->
            labels.forEach {
                    label ->
                if(labelId == label.id){
                    stringColumns.add(label.name)
                }
            }
        }

        Log.d("STATISTICS", stringColumns.toString())

        statisticsByLabelId.forEach { (labelId, stats) ->
            val secondsPerDay = MutableList(7) { 0F }

            stats.forEach {
                for ((iterator, dayOfWeek) in customDaysOfWeek.withIndex()) {
                    Calendar.MONDAY
                    val seconds = if(DateUtils.obtenerDiaDeLaSemana(it.timestamp) == dayOfWeek) it.dedicatedSeconds else 0
                    Log.d("STATISTICS", labelId.toString() + " = " + secondsPerDay[dayOfWeek-1].toString() + " + " + seconds.toString() + "($dayOfWeek == ${DateUtils.obtenerDiaDeLaSemana(it.timestamp)})")
                    secondsPerDay[iterator] += seconds.toFloat()
                }
            }

            entries[labelId] = secondsPerDay
        }

        Log.d("STATISTICS", entries.toString())

        val entryModels = entries.values.map { lista -> entriesOf(*lista.toTypedArray()) }

        _model.value = entryModelOf(*entryModels.toTypedArray())
    }

}