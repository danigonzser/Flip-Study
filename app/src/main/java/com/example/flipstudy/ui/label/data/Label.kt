package com.example.flipstudy.ui.label.data

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID
import kotlin.time.Duration

@Parcelize
@Entity(tableName = "labels")
data class Label(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "colour") val color: ColorEnum,
    @ColumnInfo(name = "dedicated_seconds") var dedicatedSeconds: Int
): Parcelable