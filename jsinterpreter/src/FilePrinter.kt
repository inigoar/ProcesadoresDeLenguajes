import res.TableSymbol
import res.Token
import res.Types
import java.io.File

class FilePrinter {

    private val tokenFileName = """Output\Tokens.txt"""
    private val symbolTableFile = """Output\SymbolTable.txt"""
    private val errorFile = """Output\Errors.txt"""
    private val parseFileName = """Output\ParseFile.txt"""


    var assignments = mutableListOf<String>()
    var delimiters = mutableListOf<String>()
    var keywords = mutableListOf<String>()
    var logicOps = mutableListOf<String>()
    var ariOps = mutableListOf<String>()
    var relationOps = mutableListOf<String>()

    var symbolTable = mutableMapOf<Int, TableSymbol>()
        get() = field
        set(value) {
            field = value
        }

    var tokenStream = mutableListOf<Token>()
        get() = field
    var auxSymbolMap = HashMap<String, Int>()

    var tokenList = mutableListOf<String>()
    var errors = mutableListOf<String>()

    var boolFunction = false
    var local = 0
    var localFunctionName = "Global"
    var functionName = ""
    var numId = 0
    var t = 0
    var checkGlobal = true
    var checkFunction = false
    var manualUpdate = false
    var tokenNumber = "0"

    fun addToken(type: Int, token: String){
        var line = ""
        when(type){
            1->{
                return
            }
            2-> {
                line = "<number, $token>"
                tokenStream.add(Token("number", token))
            }
            3->{
                line = "<cadena, $token>"
                tokenStream.add(Token("cadena", token))
            }
            4->{
                line = "<$token, >"
                tokenStream.add(Token(token, ""))
                if(token.equals("function")){
                    boolFunction = true
                }
                else if(token.equals("var")){
                 checkGlobal = false
                }
                else if(token.equals(";")){
                    checkGlobal = true
                }
                else if(boolFunction && token.equals("{")){
                    local++
                    checkFunction = true
                }
                else if(boolFunction && token.equals("}")){
                    local--
                    if(local == 0){
                        boolFunction = false
                        localFunctionName = "Global"
                        checkFunction = false
                        functionName = ""
                        numId = 0
                    }
                }
            }
            5->{
                if(boolFunction){
                    if(numId == 0) {
                            functionName = token
                    }
                    else if(numId == 1){
                            localFunctionName = functionName
                    }
                        numId++
                    }

                var symbolCheck = TableSymbol(t, token, localFunctionName)

                if(!auxSymbolMap.containsKey(token)) {
                    auxSymbolMap.put(token, t)
                }

                var auxListActual = mutableListOf<String>()
                for(simbolos in symbolTable){

                    if(simbolos.value.tableName.equals(localFunctionName)){
                        if(!auxListActual.contains(simbolos.value.lex)) {
                            auxListActual.add(simbolos.value.lex)
                        }
                    }
                }

                var auxListGlobal = mutableListOf<String>()

                for(simbolosGlobal in symbolTable){
                    if(simbolosGlobal.value.tableName.equals("Global")){
                        if(!auxListGlobal.contains(simbolosGlobal.value.lex)) {
                            auxListGlobal.add(simbolosGlobal.value.lex)
                        }
                    }
                }

                if(!auxListActual.contains(token)){
                    if(checkFunction) {
                        if(!checkGlobal){
                        symbolTable.put(t, symbolCheck)

                            if(auxListGlobal.contains(token)){
                                manualUpdate = true
                                tokenNumber = t.toString()
                            }
                            t++
                        }else{
                            if(!auxListGlobal.contains(token)){
                                auxSymbolMap.put(token, t)
                                var simboloGlobal = TableSymbol(t, token, "Global")
                                symbolTable.put(t, simboloGlobal)
                                t++

                            }
                        }
                    }else{
                        auxSymbolMap.put(token, t)
                        symbolTable.put(t, symbolCheck)
                        t++
                    }
                }

                if(auxListActual.contains(token) && !localFunctionName.equals("Global")){

                    for(simboloTablaCheck in symbolTable){

                        if(simboloTablaCheck.value.lex.equals(token) && !simboloTablaCheck.value.tableName.equals("Global")){
                            tokenNumber = simboloTablaCheck.value.id.toString()
                            manualUpdate = true
                        }

                    }
                }
                if(!manualUpdate) {
                    line = "<id, ${auxSymbolMap.get(token)}>"
                    tokenStream.add(Token("id", auxSymbolMap.get(token).toString()))
                }else{
                    line = "<id, $tokenNumber>"
                    tokenStream.add(Token("id", tokenNumber))
                    manualUpdate = false

                }
            }
        }
        tokenList.add(line)
    }

    fun isKeyword(word: String): Boolean{
        return keywords.contains(word)
    }

    fun addError(message: String){
        errors.add(message)
    }

    fun makeErrorFile(){
        val ef = File(errorFile)
        ef.createNewFile()
        ef.printWriter().use { out ->
            for(lines in errors){
                out.println(lines)
            }

        }
    }

    fun makeTokenFile(){
        val tf = File(tokenFileName)
        tf.createNewFile()
        tf.printWriter().use { out ->
            for(lines in tokenList){
                out.println(lines)
            }
        }
    }

    fun makeSymbolTableFile(){
        val sf = File(symbolTableFile)
        var tableNumber = 2
        var nameTable: String
        var nombresTablas = mutableListOf<String>()

        for(name in symbolTable){

            if(!nombresTablas.contains(name.value.tableName)){

                nombresTablas.add(name.value.tableName)

            }
        }
        sf.createNewFile()
        sf.printWriter().use { out ->

    if(nombresTablas.contains("Global")) {
        nombresTablas.remove("Global")
        out.println("TABLA GLOBAL #1:")
        out.println()
        for (symbol in symbolTable) {
            if (symbol.value.tableName.equals("Global")) {
                out.println("* LEXEMA : '${symbol.value.lex}'")
                out.println("  ATRIBUTOS :")
                if (symbol.value.type == Types.FUNCTION) {
                    out.println("  + tipo: '${symbol.value.type}'")
                    out.println("  + tipoRetorno: '${symbol.value.returnType}'")
                    var params = ""
                    for (param in symbol.value.parameterList) {
                        params += "$param, "
                    }
                    params = params.dropLast(2)
                    out.println("  + tipoParametros: '$params'")
                    out.println("  + numParametros: '${symbol.value.parameterCount}'")
                } else {
                    out.println("  + tipo: '${symbol.value.type}'")
                }


                out.println("  + id: ${symbol.value.id}")
                out.println("-------------------------")
                out.println()
            }
        }
    }
            if(nombresTablas.size>=1){
             for(name in nombresTablas) {
                 nameTable = name
                 out.println("TABLA DE LA FUNCION $nameTable #$tableNumber:")
                 out.println()

                 for (simbolo in symbolTable) {
                     if (simbolo.value.tableName.equals(nameTable)){
                         out.println("* LEXEMA : '${simbolo.value.lex}'")
                         out.println("  ATRIBUTOS :")
                         out.println("  + id: ${simbolo.value.id}")
                         out.println("  + tipo: ${simbolo.value.type}")
                         out.println()
                     }
                 }
                 out.println("-------------------------")
                 out.println()
                 tableNumber++
             }

            }
        }
    }


    fun makeOutputDir() {
        val outDir = File("/Output/")
        outDir.mkdirs()
    }

    fun makeParseFile(parseOrder: MutableList<Int>) {
        val pf = File(parseFileName)
        pf.createNewFile()
        pf.printWriter().use { out ->
            out.print("D")
            for(lines in parseOrder){
                out.print(" $lines")
            }

        }
    }

    init {
        val assignmentsFile = File("tkns/assignments.txt")
        val delimiterFile = File("tkns/delimiter.txt")
        val keywordsFile = File("tkns/keywords.txt")
        val logicOpsFile = File("tkns/logicOps.txt")
        val ariOpsFile = File("tkns/ariOps.txt")
        val relationOpsFile = File("tkns/relationOps.txt")

        var bufferedReader = assignmentsFile.bufferedReader()
        var text = bufferedReader.readLines()
        for (line in text) {
            assignments.add(line)
        }
        bufferedReader = delimiterFile.bufferedReader()
        text = bufferedReader.readLines()
        for (line in text) {
            delimiters.add(line)
        }
        bufferedReader = keywordsFile.bufferedReader()
        text = bufferedReader.readLines()
        for (line in text) {
            keywords.add(line)
        }
        bufferedReader = logicOpsFile.bufferedReader()
        text = bufferedReader.readLines()
        for (line in text) {
            logicOps.add(line)
        }
        bufferedReader = ariOpsFile.bufferedReader()
        text = bufferedReader.readLines()
        for (line in text) {
            ariOps.add(line)
        }
        bufferedReader = relationOpsFile.bufferedReader()
        text = bufferedReader.readLines()
        for (line in text) {
            relationOps.add(line)
        }
    }

}