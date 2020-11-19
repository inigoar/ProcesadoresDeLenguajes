package res

class TableSymbol (val id: Int, val lex: String, val tableName: String){
    var type: Types? = null
        get() = field
        set(value) {
            field = value
        }
    var returnType: Types? = null
        get() = field
        set(value) {
            field = value
        }

    var desplazamiento: Int? = 0
        get() = field
        set(value) {
            field = value
        }

    var parameterCount = 0
    var parameterList = mutableListOf<Types>()

    fun addParameter(type: Types){
        parameterList.add(type)
        parameterCount++
    }


}