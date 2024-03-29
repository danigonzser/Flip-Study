package com.example.flipstudy.label.data

import androidx.compose.ui.graphics.Color


/**
 * Clase enum que lista todos los colores disponibles de etiquetas
 *
 */
enum class ColorEnum {
    BLACK, RED, YELLOW, CYAN, GRAY, GREEN, BLUE, WHITE, MAGENTA
}

/**
 * Función que convierte un [ColorEnum] a [Color]
 *
 */
fun colorEnumToColor(colorEnum: ColorEnum) : Color{
    return when(colorEnum){
        ColorEnum.BLACK -> Color.Black
        ColorEnum.RED -> Color.Red
        ColorEnum.YELLOW -> Color.Yellow
        ColorEnum.CYAN -> Color.Cyan
        ColorEnum.GRAY -> Color.Gray
        ColorEnum.GREEN -> Color.Green
        ColorEnum.BLUE -> Color.Blue
        ColorEnum.WHITE -> Color.White
        ColorEnum.MAGENTA -> Color.Magenta
    }
}

/**
 * Función que convierte un [Color] a [ColorEnum]
 *
 */
fun colorToEnumColor(color : Color) : ColorEnum{
    return when(color){
        Color.Black -> ColorEnum.BLACK
        Color.Red -> ColorEnum.RED
        Color.Yellow -> ColorEnum.YELLOW
        Color.Cyan -> ColorEnum.CYAN
        Color.Gray -> ColorEnum.GRAY
        Color.Green -> ColorEnum.GREEN
        Color.Blue -> ColorEnum.BLUE
        Color.White -> ColorEnum.WHITE
        Color.Magenta -> ColorEnum.MAGENTA

        Color.DarkGray -> ColorEnum.GRAY
        Color.LightGray -> ColorEnum.GRAY
        Color.Transparent -> ColorEnum.GRAY
        Color.Unspecified -> ColorEnum.GRAY
        else -> {
            ColorEnum.GRAY
        }
    }
}