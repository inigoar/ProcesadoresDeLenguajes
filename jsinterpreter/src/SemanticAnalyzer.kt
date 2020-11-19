import res.*

class SemanticAnalyzer(ssa: SyntaxAnalyzer, private val ts: MutableMap<Int, TableSymbol>) {

    private var aux : Stack = ssa.aux
    private var stack : Stack = ssa.stack
    private var currentFunction = -1

    fun SA1() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK && (aux.elementAt(1) as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA1")
    }
    fun SA2() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK && (aux.elementAt(1) as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA2")
    }
    fun SA3() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.OK
    }
    fun SA4_1() {
        stack.pop()
        (stack.peek() as Tuple).second = (aux.peek() as Tuple).second
    }
    fun SA4_2() {
        stack.pop()
        if ((aux.elementAt(1) as Tuple).second == Types.OK){
            (aux.elementAt(4) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA4_2")
    }
    fun SA5() {
        stack.pop()
        if ((aux.elementAt(2) as Tuple).second == Types.BOOLEAN && (aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(5) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.elementAt(2) as Tuple).second} expected BOOLEAN.")
    }
    fun SA6() {
        stack.pop()
        if ((aux.elementAt(4) as Tuple).second == Types.BOOLEAN && (aux.elementAt(1) as Tuple).second == Types.OK) {
            (aux.elementAt(7) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.elementAt(4) as Tuple).second} expected BOOLEAN.")
    }
    fun SA7() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK){
            (aux.elementAt(1) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA7")
    }
    fun SA8() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK){
            (aux.elementAt(1) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA8")
    }
    fun SA9() {
        stack.pop()
        if ((aux.elementAt(1) as Tuple).second == Types.OK){
            (aux.elementAt(3) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA9")
    }
    fun SA10() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.INT
    }
    fun SA11() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.STRING
    }
    fun SA12() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.BOOLEAN
    }
    fun SA13_1() {
        stack.pop()
        if(!hasTypeGlobal((aux.peek() as Token))){
            addType((aux.peek() as Token), Types.INT)
        }//else throw Exception("Semantic Error. Variable ${getSymbol(aux.peek() as Token).lex} is already defined in the scope.")
    }
    fun SA13_2() {
        stack.pop()
        if(((aux.peek() as Tuple).second as Types) == getSymbol(aux.elementAt(1) as Token).type){
            (aux.elementAt(2) as Tuple).second = Types.OK
        }else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second as Types} expected BOOLEAN.")
    }
    fun SA14() {
        stack.pop()
        if(currentFunction == -1) throw Exception("Semantic error: return statement used outside of a function.")
        if((aux.elementAt(1) as Tuple).second == ts.get(currentFunction)!!.returnType){
            (aux.elementAt(3) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch on return statement: Found ${(aux.elementAt(1) as Tuple).second} expected ${ts.get(currentFunction)!!.returnType}.")
    }
    fun SA15() {
        stack.pop()
        if((aux.elementAt(2) as Tuple).second == Types.INT || (aux.elementAt(2) as Tuple).second == Types.STRING){
            (aux.elementAt(5) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch.\n Print output error. Found ${(aux.elementAt(2) as Tuple).second} expected INT or STRING")
    }
    fun SA16() {
        stack.pop()
        if(!hasTypeGlobal(aux.elementAt(2) as Token)){
            addType(aux.elementAt(2) as Token, Types.INT)
        }
        if(getSymbol(aux.elementAt(2) as Token).type == Types.INT || getSymbol(aux.elementAt(2) as Token).type == Types.STRING){
            (aux.elementAt(5) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.elementAt(2) as Token).type} expected INT or STRING")
    }
    fun SA17() {
        stack.pop()
        (aux.elementAt(3) as Tuple).second = (aux.elementAt(1) as Tuple).second
    }
    fun SA18() {
        stack.pop()
        if ((aux.elementAt(1) as Tuple).second == Types.INT) {
            (aux.elementAt(3) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.elementAt(1) as Tuple).second} expected INT")
    }
    fun SA19() {
        stack.pop()
        (aux.elementAt(4) as Tuple).second = (aux.elementAt(2) as Tuple).second
    }
    fun SA20_1() {
        stack.pop()
        addType((aux.peek() as Token), (aux.elementAt(1) as Tuple).second as Types)
        (stack.peek() as Tuple).second = (aux.elementAt(1) as Tuple).second
    }
    fun SA20_2() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK){
            (aux.elementAt(2) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA20_2")
    }
    fun SA21() {
        stack.pop()
        if((aux.peek() as Tuple).second == Types.OK && (aux.elementAt(1) as Tuple).second == (aux.elementAt(6) as Tuple).second){
            (aux.elementAt(3) as Tuple).second = Types.OK
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.elementAt(1) as Tuple).second} expected ${(aux.elementAt(6) as Tuple).second}")
    }
    fun SA22_1() {
        stack.pop()
        (stack.peek() as Tuple).second = (aux.elementAt(1) as Tuple).second
    }
    fun SA22_2() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK){
            (aux.elementAt(1) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA22_2")
    }
    fun SA24_1() {
        stack.pop()
        (aux.peek() as Tuple).second = (aux.elementAt(3) as Tuple).second
    }
    fun SA24_2() {
        stack.pop()
        (stack.peek() as Tuple).second = (aux.elementAt(2) as Tuple).second
    }
    fun SA26() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = (aux.peek() as Tuple).second
    }
    fun SA28() {
        stack.pop()
        if(((aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) && ((aux.peek() as Tuple).second == Types.OK)){
            (aux.elementAt(2) as Tuple).second = Types.OK
        }else throw Exception("Semantic error: SA28")
    }
    fun SA30_1() {
        stack.pop()
        currentFunction = getSymbol(aux.peek() as Token).id
        if(!hasTypeGlobal(aux.peek() as Token)){
            addReturnType(aux.peek() as Token, (aux.elementAt(1) as Tuple).second as Types)
        }else{
            throw Exception("Semantic Error: function ${ts.get(currentFunction)!!.lex} is already defined.")
        }
    }
    fun SA30_2() {
        stack.pop()
        if(((aux.elementAt(4) as Tuple).second == (aux.elementAt(1) as Tuple).second) && (aux.elementAt(1) as Tuple).second == Types.OK){
            (aux.elementAt(9) as Tuple).second = Types.OK
            currentFunction = -1
        }else throw Exception("Semantic error: SA30_2")
    }
    fun SA31() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = (aux.peek() as Tuple).second
    }
    fun SA33_1() {
        stack.pop()
        if(!hasTypeLocal(aux.peek() as Token)){
            addType(aux.peek() as Token, (aux.elementAt(1) as Tuple).second as Types)
            ts.get(currentFunction)!!.addParameter((aux.elementAt(1) as Tuple).second as Types)
        }else{
            throw Exception("Semantic Error: variable ${getSymbol(aux.peek() as Token).lex} is already defined in the scope.")
        }
    }
    fun SA33_2() {
        stack.pop()
        (aux.elementAt(3) as Tuple).second = (aux.peek() as Tuple).second
    }
    fun SA35_1() {
        stack.pop()
        if(!hasTypeLocal(aux.peek() as Token)){
            addType(aux.peek() as Token, (aux.elementAt(1) as Tuple).second as Types)
            ts.get(currentFunction)!!.addParameter((aux.elementAt(1) as Tuple).second as Types)
        }else{
            throw Exception("Semantic Error: variable ${getSymbol(aux.peek() as Token).lex} is already defined in the scope.")
        }
    }
    fun SA35_2() {
        stack.pop()
        (aux.elementAt(4) as Tuple).second = (aux.peek() as Tuple).second
    }
    fun SA37() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else if ((aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) {
            (aux.elementAt(2) as Tuple).second = (aux.peek() as Tuple).second
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected ${(aux.elementAt(1) as Tuple).second}")
    }
    fun SA39() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK || (aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) {
            (aux.elementAt(3) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected ${(aux.elementAt(1) as Tuple).second}")
    }
    fun SA41() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.BOOLEAN) (aux.elementAt(2) as Tuple).second = Types.BOOLEAN
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected BOOLEAN.")
    }
    fun SA42() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else if ((aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) {
            (aux.elementAt(2) as Tuple).second = Types.BOOLEAN
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected ${(aux.elementAt(1) as Tuple).second}")
    }
    fun SA43() {
        stack.pop()
        if((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA45() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else if ((aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) {
            (aux.elementAt(2) as Tuple).second = (aux.peek() as Tuple).second
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected ${(aux.elementAt(1) as Tuple).second}")
    }
    fun SA46() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA47() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA48() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA49() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA50() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.INT) (aux.elementAt(2) as Tuple).second = Types.INT
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected INT")
    }
    fun SA51() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = (aux.elementAt(1) as Tuple).second
        }
        else if ((aux.peek() as Tuple).second == (aux.elementAt(1) as Tuple).second) {
            (aux.elementAt(2) as Tuple).second = (aux.peek() as Tuple).second
        }
        else throw Exception("Semantic Error. Type mismatch.\nFound ${(aux.peek() as Tuple).second} expected ${(aux.elementAt(1) as Tuple).second}")
    }
    fun SA53() {
        stack.pop()
        if ((aux.peek() as Tuple).second == Types.OK) {
            (aux.elementAt(2) as Tuple).second = getSymbol((aux.elementAt(1) as Token)).type
        }else{
            val symbol = getSymbol((aux.elementAt(1) as Token))
            var type = symbol.type
            if (type == Types.FUNCTION) type = symbol.returnType!!
            if ((aux.peek() as Tuple).second == type) {
                (aux.elementAt(2) as Tuple).second = (aux.peek() as Tuple).second
            }else throw Exception("Semantic Error: Type mismatch. Expected ${(aux.peek() as Tuple).second} got $type.")
        }
    }
    fun SA54() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.INT
    }
    fun SA55() {
       stack.pop()
        (aux.elementAt(3) as Tuple).second = (aux.elementAt(1) as Tuple).second
    }
    fun SA56() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.STRING
    }
    fun SA57() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.BOOLEAN
    }
    fun SA58() {
        stack.pop()
        (aux.elementAt(1) as Tuple).second = Types.BOOLEAN
    }
    fun SA59() {
        stack.pop()
        (aux.elementAt(3) as Tuple).second = (aux.elementAt(1) as Tuple).second
    }

    fun popAux(num : Int) {
        stack.pop()
        for (i in 0 until num) aux.pop()
    }

    private fun getSymbol(token : Token): TableSymbol{
        return ts.get((token).value.toInt())!!
    }

    private fun hasTypeGlobal(token : Token): Boolean{
        var function = "Global"
        if (currentFunction != -1) function = ts.get(currentFunction)!!.lex
        val symbol = ts.get((token).value.toInt())!!
        return ((symbol.tableName == "Global" && currentFunction != -1 && symbol.type != null) || (symbol.tableName == function && symbol.type != null))
    }

    private fun hasTypeLocal(token : Token): Boolean{
        var function = "Global"
        if (currentFunction != -1) function = ts.get(currentFunction)!!.lex
        val symbol = ts.get((token).value.toInt())!!
        return (symbol.tableName == function && symbol.type != null)
    }

    private fun addType(token : Token, type : Types){
        ts.get(token.value.toInt())!!.type = type
    }

    private fun addReturnType(token : Token, type : Types){
        ts.get(token.value.toInt())!!.type = Types.FUNCTION
        ts.get(token.value.toInt())!!.returnType = type
    }

}