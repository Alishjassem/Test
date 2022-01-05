package com.magma.aomlati.utils

class Const {

    companion object {
        const val DATABASE_NAME: String = "AMMOULATI_DB"
        const val PREF_NAME: String = "AMMOULATI"
        //Network constants
        const val TIMEOUT_CONNECT = 60L   //In seconds
        const val TIMEOUT_READ = 60L   //In seconds
        const val TIMEOUT_WRITE = 60L   //In seconds
        const val PROXIMITY_RADIUS = 10000
        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiVTJGc2RHVmtYMThFVndHemVQWEU0QmNaMG1JQjNoWDcraGhUSWZEc2YvL3o1NXBZcHlmUDYyOXhkNlp6ejdrSHBQRUZBSEVkUE1XZG5zcW9nb3Q5bEhOM1k0ZjhKTUNRYVZUWU92LzZFR0k9IiwiaWF0IjoxNjQwNjQwNDI0LCJleHAiOjE2NDA3MjY4MjR9.E_rwFMHpKNslucLVcQuz0VJdU7Uq1qXESrV08SnN1gU"
        const val DATE_FORMAT = "yyyy-MM-dd_HHmmss"

        const val TAG_FoodMenuFragment = "FoodMenuFragment"
        const val TAG_ForgetPasswordFragment = "ForgetPasswordFragment"
        const val TAG_DatePickerParentFragment = "DatePickerParentFragment"
        const val TAG_TimePickerParentFragment = "TimePickerParentFragment"

        //Type
        const val TYPE_CURRENCY_FAVORITE = 1
        const val TYPE_CURRENCY_SETTING_FAVORITE = 2
        const val TYPE_CURRENCY_EXPAND = 3
        const val TYPE_CURRENCY_COLLAPSE = 4

        const val TYPE_FIAT = "fiat"
        const val TYPE_CRYPTO = "crypto"

        //shared pref
        const val PREF_API_TOKEN = "api_token"
        const val PREF_LANG = "lang"
        const val PREF_IS_SHOWN_ONBOARDING = "is_shown_onboarding"
        const val PREF_IS_DARK_THEME = "is_dark_theme"

        //Extras
        const val EXTRA_REGISTER_REQUEST = "register_request"

    }
}