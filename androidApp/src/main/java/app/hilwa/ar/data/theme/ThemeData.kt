package app.hilwa.ar.data.theme

import app.hilwa.ar.R

enum class ThemeData(val value: String, val text: Int) {
    DEFAULT("default", R.string.system),
    DARK("dark", R.string.dark),
    LIGHT("light", R.string.light)
}